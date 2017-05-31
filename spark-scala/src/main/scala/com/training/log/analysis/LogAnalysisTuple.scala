package com.training.log.analysis

import org.apache.kafka.clients.producer.{ KafkaProducer, ProducerConfig, ProducerRecord }
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.sql.SaveMode
import org.apache.spark.{ SparkContext, SparkConf }
import org.apache.spark.sql.SQLContext
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.rdd.{ PairRDDFunctions, RDD }

case class LogRecord(clientIp: String, clientIdentity: String, user: String, dateTime: String, request: String, statusCode: Int, bytesSent: Long)

object LogAnalysisTuple {
  val PATTERN = """^(\S+) (\S+) (\S+) \[([\w:/]+\s[+\-]\d{4})\] "(\S+) (\S+)\s*(\S*)\s*" (\d{3}) (\S+)""".r

  def parseLogLine(log: String): LogRecord = {
    try {
      val res = PATTERN.findFirstMatchIn(log)
      if (res.isEmpty) {
        println("Rejected Log Line: " + log)
        LogRecord("Empty", "-", "-", "", "", -1, -1)
      } else {
        val m = res.get
        // NOTE:   HEAD does not have a content size.
        if (m.group(9).equals("-")) {
          LogRecord(m.group(1), m.group(2), m.group(3), m.group(4),
            m.group(5) + m.group(6) + m.group(7), m.group(8).toInt, 0)
        } else {
          LogRecord(m.group(1), m.group(2), m.group(3), m.group(4),
            m.group(5) + m.group(6) + m.group(7), m.group(8).toInt, m.group(9).toLong)
        }
      }
    } catch {
      case e: Exception =>
        println("Exception on line:" + log + ":" + e.getMessage);
        LogRecord("Empty", "-", "-", "", "-", -1, -1)
    }
  }
  
   def LogRecordToPairRDDFunctions[K, V]
  (rdd: RDD[LogRecord]): PairRDDFunctions[String, (String, String,String,String,Int ,Long )] =
    new PairRDDFunctions(
      rdd.map {
        case LogRecord(k, v1, v2,v3,v4,v5,v6) => k -> (v1 , v2 ,v3 ,v4 ,v5 ,v6) 
      }
    )

  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum><group> <topics> <numThreads>")
      System.exit(1)
    }
    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setAppName("KafkaLogAnalysis")

    val ssc = new StreamingContext(sparkConf, Seconds(10))
    ssc.checkpoint("checkpoint")
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val accessLogs = lines.map(parseLogLine).filter(!_.clientIp.equals("Empty")).cache()

    
    var i = 0
    accessLogs.foreachRDD(t => {
      i = i + 1
      
      val storeHbase = t.filter(_.statusCode < 400)
      if (!storeHbase.isEmpty()) {

        val pairStoreHbaeRdd = storeHbase.map(x => LogRecord)
        storeHbase.foreach(r => {
          val hConf = new HBaseConfiguration()
          val hTable = new HTable(hConf, "logAnalysis")
          val thePut = new Put(Bytes.toBytes(r.clientIp + "_"+ r.dateTime))
          thePut.add(Bytes.toBytes("logdetails"), Bytes.toBytes("ip"), Bytes.toBytes(r.clientIp))
          thePut.add(Bytes.toBytes("logdetails"), Bytes.toBytes("datetime"), Bytes.toBytes(r.dateTime))
          thePut.add(Bytes.toBytes("logdetails"), Bytes.toBytes("request"), Bytes.toBytes(r.request))
          thePut.add(Bytes.toBytes("logdetails"), Bytes.toBytes("statusCode"), Bytes.toBytes(r.statusCode))
          thePut.add(Bytes.toBytes("logdetails"), Bytes.toBytes("bytesSent"), Bytes.toBytes(r.bytesSent))
          
          hTable.put(thePut);

        })
        
        
      /*    
        val storeHbaseI = storeHbase.map(x => (x.clientIp, x.dateTime, x.request, x.statusCode, x.bytesSent))
        
        //val storeMap = storeHbaseI.map[(String, (String ,String, Int, Long))] (x=>{_.1,(_.2, _.3, _.4, _.5)})
        saveAsTextFileAndMerge("hdfs://localhost:9000/user/manhati/storeHbase", "storeHbase.txt_" + i, storeHbaseI)*/
        
      }

      val storeFolder = t.filter(_.statusCode >= 400)
      if (!storeFolder.isEmpty()) {
        val storeFolderI = storeFolder.map(x => (x.clientIp, x.dateTime, x.request, x.statusCode, x.bytesSent))
        saveAsTextFileAndMerge("hdfs://localhost:9000/user/manhati/storeFolder", "storeFolder.txt_" + i, storeFolderI)
      }
    })

    ssc.start()
    ssc.awaitTermination()

  }
  import org.apache.hadoop.fs._
  import org.apache.spark.rdd.RDD
  import org.apache.hadoop.conf.Configuration
  def saveAsTextFileAndMerge[T](hdfsServer: String, fileName: String, rdd: RDD[T]) = {
    val sourceFile = hdfsServer + "/tmp/"
    rdd.saveAsTextFile(sourceFile)
    val dstPath = hdfsServer + "/final/"
    merge(sourceFile, dstPath, fileName)
  }

  def merge(srcPath: String, dstPath: String, fileName: String): Unit = {
    val hadoopConfig = new Configuration()
    val hdfs = FileSystem.get(hadoopConfig)
    val destinationPath = new Path(dstPath)
    if (!hdfs.exists(destinationPath)) {
      hdfs.mkdirs(destinationPath)
    }
    FileUtil.copyMerge(hdfs, new Path(srcPath), hdfs, new Path(dstPath + "/" + fileName), true, hadoopConfig, null)
  }
}
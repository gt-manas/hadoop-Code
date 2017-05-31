/*package com.training.log.analysis

import org.apache.kafka.clients.producer.{ KafkaProducer, ProducerConfig, ProducerRecord }
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.sql.SaveMode  
import org.apache.spark.{SparkContext, SparkConf}  

case class LogRecord(clientIp: String, clientIdentity: String, user: String, dateTime: String, request: String, statusCode: Int, bytesSent: Long)

object LogAnalysis {
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
            m.group(5)+ m.group(6) + m.group(7), m.group(8).toInt, 0)
        } else {
          LogRecord(m.group(1), m.group(2), m.group(3), m.group(4),
            m.group(5)+ m.group(6) + m.group(7), m.group(8).toInt, m.group(9).toLong)
        }
      }
    } catch {
      case e: Exception =>
        println("Exception on line:" + log + ":" + e.getMessage);
        LogRecord("Empty", "-", "-", "", "-", -1, -1)
    }
  }
  
  
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
       
    
    accessLogs.foreachRDD(t => {
      val storeHbase = t.filter(_.statusCode < 400)
      val storeHbaseI = storeHbase.map(x => (x.clientIp, x.dateTime, x.request, x.statusCode, x.bytesSent))
      storeHbaseI.saveAsTextFile("hdfs://localhost:9000/user/manhati/storeHbase")

      val storeFolder = t.filter(_.statusCode >= 400)
      val storeFolderI = storeFolder.map(x => (x.clientIp, x.dateTime, x.request, x.statusCode, x.bytesSent))
      storeFolderI.saveAsTextFile("hdfs://localhost:9000/user/manhati/storeFolder")

    })
    
      ssc.start()
      ssc.awaitTermination()

  }

}*/
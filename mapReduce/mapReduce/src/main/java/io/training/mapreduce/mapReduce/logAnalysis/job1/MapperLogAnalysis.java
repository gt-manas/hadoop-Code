package io.training.mapreduce.mapReduce.logAnalysis.job1;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperLogAnalysis extends Mapper<LongWritable, Text, LogAnalysisKey, IntWritable> {
	String rex ="^(\\S+) (\\S) (\\S) \\[(\\d{2})\\/([a-zA-Z]{3})\\/(\\d{4}):\\d{2}:\\d{2}:\\d{2} -\\d{4}] \"(\\S+ \\S+\\s*\\S*\\s*)\" (\\d{3}) (\\S+) \"(\\S+)\" \"(.+)\"";
	public void map (LongWritable key, Text value , Context context ) throws IOException, InterruptedException{
			String logLine = value.toString();
			Pattern pattern = Pattern.compile(rex);
			Matcher matcher = pattern.matcher(logLine);
			int i=0;
			int status =0 ;
			String ip ="";
			Text ipTxt;
			IntWritable statusIntW;
			while (matcher.find()) {
				System.out.println( this.getClass() + "while Loop ------ " + i++);
				ip = matcher.group(1); //Ip
				try {
				 status =  Integer.parseInt(matcher.group(8)); // status
				} catch(Exception e) {
					System.out.println(" Exception while Converting ");
					status =999;
				}
			}
			ipTxt = new Text(ip);
			statusIntW = new IntWritable(status);
			LogAnalysisKey keyOut = new  LogAnalysisKey(ipTxt,statusIntW);
			context.write(keyOut,new IntWritable(1));
			
		}
	
}

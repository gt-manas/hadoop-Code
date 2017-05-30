package io.training.mapreduce.mapReduce.logAnalysis.job2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperLogAnalysisSecondarySort extends Mapper<Text, IntWritable, LogAnalysisKeySecondarySort, IntWritable> {
	public void map (Text key, IntWritable value , Context context ) throws IOException, InterruptedException{
		System.out.println("***************** key key.toString()  " + key.toString());
		
		String[] keys = key.toString().split(",");
		LogAnalysisKeySecondarySort keyOut = new LogAnalysisKeySecondarySort(new Text(keys[0]), new IntWritable(1));
		//IntWritable OutVal = new IntWritable(999);
		context.write(keyOut, value);
	}
}

package io.training.mapreduce.mapReduce.logAnalysis.job2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperLogAnalysisSecondarySort extends Mapper<LongWritable, Text, LogAnalysisKeySecondarySort, IntWritable> {
	public void map (LongWritable key, Text value , Context context ) throws IOException, InterruptedException{
		String[] values = value.toString().split(",");
		LogAnalysisKeySecondarySort keyOut = new LogAnalysisKeySecondarySort(new Text(values[0]), new IntWritable(Integer.parseInt(values[1])));
		IntWritable OutVal = new IntWritable(Integer.parseInt(values[2]));
		context.write(keyOut, OutVal);
	}
}

package io.training.mapreduce.mapReduce.logAnalysis.job2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class ReducerLogAnalysisSecondarySort extends Reducer<LogAnalysisKeySecondarySort, IntWritable, Text, IntWritable> {
	public void reduce(LogAnalysisKeySecondarySort key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int count = 0;
		for (IntWritable value : values) {
			count = value.get();
		}
		
		Text keyOut = new Text(key.getIpString() +"," + key.getStatusString());
		IntWritable valOut = new IntWritable(count);
		context.write(keyOut, valOut);
	}
}

package io.training.mapreduce.mapReduce.logAnalysis.job1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReducerLogAnalysis extends Reducer<LogAnalysisKey, IntWritable, Text, IntWritable> {
	public void reduce(LogAnalysisKey key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		int totalCount = 0;
		for (IntWritable value : values) {
			totalCount += value.get();
		}
		String outKeuStr = key.getIpString() + "," + key.getStatusString();
		context.write(new Text(outKeuStr), new IntWritable(totalCount));
	}
}

package io.training.mapreduce.mapReduce.logAnalysis.job2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class NaturalKeyPartitioner extends Partitioner<LogAnalysisKeySecondarySort, IntWritable> {

	@Override
	public int getPartition(LogAnalysisKeySecondarySort key, IntWritable count, int noOfPartitioner) {

		int hash = key.getIp().hashCode();
		int partition = hash % noOfPartitioner;

		return partition;
	}

}

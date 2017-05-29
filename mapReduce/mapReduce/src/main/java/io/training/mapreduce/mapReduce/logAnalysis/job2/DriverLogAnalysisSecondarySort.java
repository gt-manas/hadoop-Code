package io.training.mapreduce.mapReduce.logAnalysis.job2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class DriverLogAnalysisSecondarySort extends Configured implements Tool {

	public int run(String[] args) throws Exception {
		if (args.length != 2) {
	        System.err.println("Usage: Driver <in> <out>");
	        System.exit(2);
	    }

		Configuration conf = new Configuration();
		conf.set("mapred.textoutputformat.separator", ",");
		
		Job job = Job.getInstance(conf);
		
		job.setJobName(" Log Analysis Job 2");
		job.setJarByClass(getClass());
		
		job.setPartitionerClass(NaturalKeyPartitioner.class);
        job.setGroupingComparatorClass(NaturalKeyGroupingComparator.class);
        job.setSortComparatorClass(CompositeKeyComparator.class);
         
		
		job.setMapperClass(MapperLogAnalysisSecondarySort.class);
		job.setReducerClass(ReducerLogAnalysisSecondarySort.class);
		
		job.setMapOutputKeyClass(LogAnalysisKeySecondarySort.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 1 : 0;
	}

	public static void main(String args[]) throws Exception{
		ToolRunner.run(new Configuration(), new DriverLogAnalysisSecondarySort(), args);
	}
}

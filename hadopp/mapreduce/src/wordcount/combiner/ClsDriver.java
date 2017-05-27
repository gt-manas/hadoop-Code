package wordcount.combiner;

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

public class ClsDriver extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {
		Job job = Job.getInstance();
		job.setJobName("Word Count");
		job.setJarByClass(getClass());
		job.setMapperClass(ClsMapper.class);
		job.setCombinerClass(ClsCombiner.class);
		job.setReducerClass(ClsReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.setInputPaths(job, new Path(arg0[0]));
		FileOutputFormat.setOutputPath(job, new Path(arg0[1]));
		return job.waitForCompletion(true) ? 1 : 0;
	}

	public static void main(String args[]) {
		try {
			ToolRunner.run(new Configuration(), new ClsDriver(), args);
		} catch (Exception e) {
			System.out.println("MANAS");

		}

	}

}

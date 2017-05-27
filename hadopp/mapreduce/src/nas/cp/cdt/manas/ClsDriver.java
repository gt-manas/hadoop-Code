package nas.cp.cdt.manas;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class ClsDriver extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {
		
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ",");
		
		Job job = Job.getInstance(conf);
		
		job.setJobName("nasClsAgeAvgScore");
		job.setJarByClass(getClass());
		
		job.setNumReduceTasks(2);
		
		job.setMapperClass(ClsMapper.class);
		job.setReducerClass(ClsReducer.class);
		job.setPartitionerClass(ClsPartitioner.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ClsAgeAvgScore.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path(arg0[0]));
		FileOutputFormat.setOutputPath(job, new Path(arg0[1]));
		
		return job.waitForCompletion(true)? 0:1;
	}

	public static void main(String[] args) throws Exception {
		int rc  = ToolRunner.run(new Configuration(), new ClsDriver(), args);
		System.exit(rc);
	}

}

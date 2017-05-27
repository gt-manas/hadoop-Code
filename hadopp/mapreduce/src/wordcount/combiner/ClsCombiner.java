package wordcount.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClsCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text key, Iterable<IntWritable> values, Context cotext) {
		try {
			int count = 0;
			for (IntWritable value : values) {
				count += value.get();

			}
			cotext.write(key, new IntWritable(count));
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		}

	}

}

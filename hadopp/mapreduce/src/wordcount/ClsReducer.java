package wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClsReducer extends Reducer<Text, IntWritable, Text, LongWritable> {
	public void reduce( Text key ,Iterable <IntWritable> values, Context cotext){
		try{
			long count =0;
			for (IntWritable value : values){
				count += value.get();
				
			}
			cotext.write(key, new LongWritable(count));
			
		}catch(Exception e){
			
		}
		
	}

}

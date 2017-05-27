package nas.cp;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClsReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
	
	public void reduce(Text key , Iterable<IntWritable> value ,Context cotext){
		try{
			int totalScore =0, count =0;
			for (IntWritable v: value){
				totalScore += v.get();
				count++;
				
			}
			cotext.write(key, new FloatWritable((float) totalScore/count));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

}

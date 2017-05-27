package wordcount;

import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClsMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
	public void map (LongWritable key, Text value , Context context ){
		
		try{
			StringTokenizer tokenizer = new StringTokenizer(value.toString());
			while ( tokenizer.hasMoreTokens()){
				context.write(new Text(tokenizer.nextToken()),new IntWritable(1));
				
			}
		}catch(Exception e){
			System.out.println("exception " + e.getMessage());
		}
	}

}

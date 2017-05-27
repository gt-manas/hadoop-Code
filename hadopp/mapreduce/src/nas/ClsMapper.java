package nas;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClsMapper extends Mapper<Text, Text, Text, IntWritable> {
	public void map(Text key , Text value , Context context){
		String[] values = value.toString().split(",");
		try {
			context.write(new Text(values[1]), new IntWritable(Integer.parseInt(values[2])));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

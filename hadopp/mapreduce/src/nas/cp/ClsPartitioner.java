package nas.cp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ClsPartitioner extends Partitioner<Text, IntWritable> {

	@Override
	public int getPartition(Text key, IntWritable value, int arg2) {
		if(key.toString().equalsIgnoreCase("m")){
			return 0;
		}else {
			return 1;
		}
	}
	

}

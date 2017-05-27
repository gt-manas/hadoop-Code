package nas.cpmanas;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ClsPartitioner extends Partitioner<Text, Text> {

	@Override
	public int getPartition(Text key, Text value, int numReducers) {
		String[] values = value.toString().split(",");
		//values[0] - 32 - age
		//values[1] - 95 - score
		
		if(Integer.parseInt(values[0])<=30)
			return 0;
		else
			return 1;
	}

}

package nas.cp.cdt;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ClsPartitioner extends Partitioner<Text, AgeScoreWritable> {

	@Override
	public int getPartition(Text key, AgeScoreWritable value, int numReducers) {
		if(value.age.get()<=30)
			return 0;
		else
			return 1;
	}

}

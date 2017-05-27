package nas.cp.cdt.manas;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ClsPartitioner extends Partitioner<Text, ClsAgeAvgScore> {

	@Override
	public int getPartition(Text key, ClsAgeAvgScore value, int noReducer) {
		if(value.age.get()<=30) return 0;
		else 	return 1;
	}
	

}

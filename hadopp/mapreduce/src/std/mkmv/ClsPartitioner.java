package std.mkmv;

import org.apache.hadoop.mapreduce.Partitioner;

public class ClsPartitioner extends Partitioner<GenderCastKey, ClsAgeAvgScore> {

	@Override
	public int getPartition(GenderCastKey key, ClsAgeAvgScore value, int noReducer) {
		if(key.getSexString().equalsIgnoreCase("m") && (key.getCastString().equalsIgnoreCase("gen")|| key.getCastString().equalsIgnoreCase("obc")))
		{
			return 0;
		}else if (key.getSexString().equalsIgnoreCase("F") && (key.getCastString().equalsIgnoreCase("st")|| key.getCastString().equalsIgnoreCase("sc"))){
			return 1;
		}
		return 1;
	}

}

package std.mkmv;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClsReducer extends Reducer<GenderCastKey, ClsAgeAvgScore, Text, Text> {
	public void reduce(GenderCastKey key, Iterable<ClsAgeAvgScore> values, Context context) throws IOException, InterruptedException {
		int totalScore = 0, totalAge = 0 ,count = 0;
		for (ClsAgeAvgScore value : values) {
			totalScore += value.score.get();
			totalAge += value.age.get();
			count++;
		}
		float avgScore =  (float) totalScore/ count;
		float avgAge = (float) totalAge/ count;
		
		String outValStr = avgAge+ "," + avgScore;
		String outKeuStr = key.getSexString()+ ","+key.getCastString();
		
		context.write(new Text(outKeuStr), new Text(outValStr));
	}
}

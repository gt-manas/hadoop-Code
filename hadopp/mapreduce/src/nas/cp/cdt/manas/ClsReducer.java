package nas.cp.cdt.manas;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClsReducer extends Reducer<Text, ClsAgeAvgScore, Text, FloatWritable> {
	public void reduce(Text key,Iterable<ClsAgeAvgScore> values, Context context){
		try{
			
		int totalScore = 0,count = 0;
		for(ClsAgeAvgScore value : values)
		{
			totalScore += value.score.get();
			count++;
		}
		context.write(key, new FloatWritable((float)totalScore/count));
		
		}catch(Exception e){
			System.out.println(e.getMessage());
			
		}
	}

}

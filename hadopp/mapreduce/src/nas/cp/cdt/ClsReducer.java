package nas.cp.cdt;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClsReducer extends Reducer<Text, AgeScoreWritable, Text, FloatWritable> 
{
	public void reduce(Text key, Iterable<AgeScoreWritable> values, Context context)
	{
		try
		{
			//Key - m
			//values - {(32,77),(33,87),(23,67)}
			int totalScore = 0,count = 0;
			
			for(AgeScoreWritable value : values)
			{
				totalScore += value.score.get();
				count++;
			}
			
			context.write(key, new FloatWritable((float)totalScore/count));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

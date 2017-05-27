package nas.cpmanas;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClsReducer extends Reducer<Text, Text, Text, FloatWritable> 
{
	public void reduce(Text key, Iterable<Text> values, Context context)
	{
		try
		{
			//Key - m
			//values - {(32,77),(33,87),(23,67)}
			int totalScore = 0,count = 0;
			
			for(Text value : values)
			{
				String[] inValues = value.toString().split(",");
				totalScore += Integer.parseInt(inValues[1]);
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

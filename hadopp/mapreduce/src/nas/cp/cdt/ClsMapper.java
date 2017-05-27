package nas.cp.cdt;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClsMapper extends Mapper<Text, Text, Text, AgeScoreWritable> 
{
	public void map(Text key, Text value, Context context)
	{
		try
		{
			String[] values = value.toString().split(",");
			
			AgeScoreWritable outValue = new AgeScoreWritable();
			
			outValue.age.set(Integer.parseInt(values[0]));
			outValue.score.set(Integer.parseInt(values[2]));
			
			context.write(new Text(values[1]), outValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

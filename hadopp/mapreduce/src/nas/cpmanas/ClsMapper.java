package nas.cpmanas;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClsMapper extends Mapper<Text, Text, Text, Text> 
{
	public void map(Text key, Text value, Context context)
	{
		try
		{
			//Key - Jessica
			//Value - 32,F,95
			String[] values = value.toString().split(",");
			//values[0] - 32
			//values[1] - F
			//values[2] - 95
			String outValue = values[0].trim()+","+values[2].trim();
			//32,95
			context.write(new Text(values[1]), new Text(outValue));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

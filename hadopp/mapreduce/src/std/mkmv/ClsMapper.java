package std.mkmv;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClsMapper extends Mapper<Text, Text, GenderCastKey, ClsAgeAvgScore> {
	public void map(Text key, Text value, Context context ) throws IOException, InterruptedException{
		
		String[] values = value.toString().split(",") ;
		
		Text cast = new Text((values[0]).trim());
		Text sex =  new Text((values[2]).trim());
		GenderCastKey keyOut = new GenderCastKey(sex,cast);
		ClsAgeAvgScore valueOut = new ClsAgeAvgScore();
		
		valueOut.age.set(Integer.parseInt(values[1]));
		valueOut.score.set(Integer.parseInt(values[3]));
		
		context.write(keyOut, valueOut);
	}
}

package nas.cp.cdt.manas;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ClsMapper extends Mapper<Text, Text, Text, ClsAgeAvgScore> {
	public void map(Text key, Text value,Context context) {
		try {
			String[] values = value.toString().split(",");
			ClsAgeAvgScore ageAvgScore = new ClsAgeAvgScore();
			ageAvgScore.age.set(Integer.parseInt(values[0]));
			ageAvgScore.score.set(Integer.parseInt(values[2]));
		
			context.write(new Text(values[1]), ageAvgScore);
			}  catch (Exception e) {
			System.out.println(e.getMessage());
			}
		
	}

}

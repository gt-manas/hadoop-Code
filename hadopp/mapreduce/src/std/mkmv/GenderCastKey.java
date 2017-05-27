package std.mkmv;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class GenderCastKey implements WritableComparable<GenderCastKey> {
	 private Text sex = new Text();
	 private Text cast = new Text();
	
	public GenderCastKey(){
		set(new Text(),new Text());
	}
	
	public GenderCastKey(Text sex , Text cast ){
		set(sex,cast);
	}
	
	public void set(Text sex, Text cast){
		this.sex = sex;
		this.cast = cast;
	}
	
	public Text getSex(){
		return sex;
	}
	public String getSexString(){
		return sex.toString();
	}
	
	public Text getCast(){
		return cast;
	}
	
	public String getCastString(){
		return cast.toString();
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		sex.readFields(in);
		cast.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		sex.write(out);
		cast.write(out);
	}


	@Override
	public int compareTo(GenderCastKey gck) {
		int compSex = sex.compareTo(gck.sex);
		int copCast = cast.compareTo(gck.cast);
		if(compSex!=0){
			return compSex;
		}
		return copCast;
	}

}

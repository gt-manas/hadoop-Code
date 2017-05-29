package io.training.mapreduce.mapReduce.logAnalysis.job2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class LogAnalysisKeySecondarySort implements WritableComparable<LogAnalysisKeySecondarySort> {

	private Text ip = new Text();
	private IntWritable status = new IntWritable();
	
	public LogAnalysisKeySecondarySort() {
		set(new Text(), new IntWritable());
	}
	
	public void set(Text ip, IntWritable status) {
		this.ip=ip;
		this.status=status;
	}
	
	public Text getIp() {
		return ip;
	}
	
	public String getIpString() {
		return ip.toString();
	}

	public IntWritable getStatus() {
		return status;
	}
	
	public String getStatusString() {
		return status.toString();
	}
	

	public LogAnalysisKeySecondarySort(Text ip, IntWritable status) {
		set(ip,status);
	}
	
	
	
	public void readFields(DataInput in) throws IOException {
		ip.readFields(in);
		status.readFields(in);	
	}

	public void write(DataOutput out) throws IOException {
		ip.write(out);
		status.write(out);
	}

	public int compareTo(LogAnalysisKeySecondarySort lak) {
		int compareIp = ip.compareTo(lak.ip);
		int compStatus = status.compareTo(lak.status);
		if (compareIp!= 0 ) {
			return compareIp;
		}
		return compStatus;
	}

}

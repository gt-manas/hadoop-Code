package io.training.mapreduce.mapReduce.logAnalysis.job2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class NaturalKeyGroupingComparator extends WritableComparator {
	protected NaturalKeyGroupingComparator() {
        super(LogAnalysisKeySecondarySort.class, true);
    }   
	@SuppressWarnings("rawtypes")
	@Override
    public int compare(WritableComparable wc1, WritableComparable wc2) {
    	LogAnalysisKeySecondarySort key1 = (LogAnalysisKeySecondarySort)wc1;
    	LogAnalysisKeySecondarySort key2 = (LogAnalysisKeySecondarySort)wc2;
         
        return key1.getIp().compareTo(key2.getIp());
    }
	
}

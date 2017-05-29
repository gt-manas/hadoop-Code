package io.training.mapreduce.mapReduce.logAnalysis.job2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class CompositeKeyComparator extends WritableComparator {

	 protected CompositeKeyComparator() {
	        super(LogAnalysisKeySecondarySort.class, true);
	    }   
	    @SuppressWarnings("rawtypes")
	    @Override
	    public int compare(WritableComparable wc1, WritableComparable wc2) {
	    	LogAnalysisKeySecondarySort k1 = (LogAnalysisKeySecondarySort) wc1;
	    	LogAnalysisKeySecondarySort k2 = (LogAnalysisKeySecondarySort) wc2;
	         
	        int result = k1.getIp().compareTo(k2.getIp());
	        if(0 == result) {
	            result = -1* k1.getStatus().compareTo(k2.getStatus());
	        }
	        return result;
	    }
	
	
}

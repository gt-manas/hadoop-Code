package io.pig.udf;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

public class LogParsing extends EvalFunc<Tuple> {

	String rex ="^(\\S+) (\\S) (\\S) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+)\\s*(\\S*)\\s*\" (\\d{3}) (\\S+) \"(\\S+)\" \"(.+)\"";
	@Override
	public Tuple exec(Tuple input) throws IOException {
			if(input!= null &&  input.size() !=0) {
				String logLine = (String) input.get(0);
				Pattern pattern = Pattern.compile(rex);
				Matcher matcher = pattern.matcher(logLine);
				Tuple returnTuple = TupleFactory.getInstance().newTuple();
				while (matcher.find()) {
					returnTuple.append(matcher.group(1));
				    System.out.println("group 1: " + matcher.group(1));
				    System.out.println("group 2: " + matcher.group(2));
				    System.out.println("group 3: " + matcher.group(3));
				}
				
			}
		return null;
	}
	
	
	
	

}

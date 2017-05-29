

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.impl.logicalLayer.schema.Schema.FieldSchema;

public class LogParsing extends EvalFunc<Tuple> {

	String rex ="^(\\S+) (\\S) (\\S) \\[(\\d{2})\\/([a-zA-Z]{3})\\/(\\d{4}):\\d{2}:\\d{2}:\\d{2} -\\d{4}] \"(\\S+ \\S+\\s*\\S*\\s*)\" (\\d{3}) (\\S+) \"(\\S+)\" \"(.+)\"";
	@Override
	public Tuple exec(Tuple input) throws IOException {
		Tuple returnTuple = TupleFactory.getInstance().newTuple(7);
			if(input!= null &&  input.size() !=0) {
				String logLine = (String) input.get(0);
				Pattern pattern = Pattern.compile(rex);
				Matcher matcher = pattern.matcher(logLine);
				while (matcher.find()) {
					returnTuple.set(0,matcher.group(1));
					returnTuple.set(1,matcher.group(4));
					returnTuple.set(2,matcher.group(5));
					returnTuple.set(3,matcher.group(6));
					returnTuple.set(4,matcher.group(7));
					returnTuple.set(5,matcher.group(8));
					returnTuple.set(6,matcher.group(9));
				    System.out.println("group 1: " + matcher.group(1));
				    System.out.println("group 2: " + matcher.group(2));
				    System.out.println("group 3: " + matcher.group(3));
				    System.out.println("group 4: " + matcher.group(4));
				}
				
			}
		return returnTuple;
	}
	
	
	 public Schema outputSchema(Schema input) {
	        try{
	            Schema tupleSchema = new Schema();
	            tupleSchema.add(new FieldSchema("ip", DataType.CHARARRAY));
	            tupleSchema.add(new FieldSchema("day",DataType.CHARARRAY));
	            tupleSchema.add(new FieldSchema("month",DataType.CHARARRAY));
	            tupleSchema.add(new FieldSchema("year",DataType.CHARARRAY));
	            tupleSchema.add(new FieldSchema("resReq",DataType.CHARARRAY));
	            tupleSchema.add(new FieldSchema("res",DataType.CHARARRAY));
	            tupleSchema.add(new FieldSchema("dataByte",DataType.CHARARRAY));
	            Schema schema = new Schema(new FieldSchema(null, tupleSchema));
	           
	            return schema;
	        }catch (Exception e){
	                return null;
	        }
	    }
	

}

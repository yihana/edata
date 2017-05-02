package booksearch_mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

import com.mongodb.BasicDBObject;

public class BookSearchMapper extends Mapper<Object, BSONObject, Text, IntWritable> {
	
	private final static IntWritable ONE = new IntWritable();
	private Text word = new Text();
	
	protected void map(Object key, BSONObject value, Context context) 
		throws IOException, InterruptedException {
		BasicDBObject anItem = (BasicDBObject)value.get("search-book");
		BasicDBObject volumeInfo = (BasicDBObject)anItem.get("volumeInfo");
		String description = volumeInfo.getString("description");
		if(description == null || description.trim().length() <= 0) return;
		
		StringTokenizer st = new StringTokenizer(description);
		while(st.hasMoreTokens()) {
			word.set(st.nextToken());
			context.write(word, ONE);
		}
	}
}

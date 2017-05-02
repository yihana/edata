package booksearch_mapreduce;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class BookSearcherReducer extends
	Reducer<Text, IntWritable, Text, IntWritable> {
	
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) 
		throws IOException, InterruptedException {
		int sum = 0;
		for(final IntWritable value : values) sum += value.get();
		context.write(key, new IntWritable(sum));
	}
}
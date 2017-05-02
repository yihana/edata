package booksearch_mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import com.mongodb.hadoop.util.MongoTool;

public class MongoJob extends MongoTool {
	static {
		Configuration.addDefaultResource("mongo-default.xml");
		Configuration.addDefaultResource("mongo-book.xml");
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		JobHelper.addJarForJob(conf, "/Users/skcc/.m2/repository/org/mongodb/mongo-hadoop/mongo-hadoop-core/2.0.2/mongo-hadoop-core-2.0.2.jar:"
					+ "/Users/skcc/.m2/repository/org/mongodb/mongo-java-driver/3.4.2/mongo-java-driver-3.4.2.jar");
		
		System.exit(ToolRunner.run(conf, new MongoJob(), args));
	}
}

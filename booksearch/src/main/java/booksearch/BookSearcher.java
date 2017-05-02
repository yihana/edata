package booksearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

public class BookSearcher {
	public String searchBooks(String keyword) {
		URL url = null;
		try {
			url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + keyword);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		StringBuffer sb = new StringBuffer();
		String line;
		try {
			URLConnection urlConn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));
			while((line = br.readLine()) != null) sb.append(line);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public void saveBooks(String books) {
		Mongo mongo = null;
		try{
			mongo = new MongoClient("localhost", 27017);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		mongo.setWriteConcern(new WriteConcern(1, 2000));
		DB bookDB = mongo.getDB("books-db");
		DBCollection bookColl = bookDB.getCollection("books");
		
		try{
			JSONObject json = new JSONObject(books);
			JSONArray items = json.getJSONArray("items");
			for( int i=0; i<items.length(); i++) {
				DBObject doc = new BasicDBObject();
				doc.put("search-book", (DBObject)JSON.parse(items.getJSONObject(i).toString()));
				bookColl.save(doc);
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
}

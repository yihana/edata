package nsearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

public class ncrawler2 {
	
    public JSONObject market() {
        String clientId = "EDZ9horhUGj_TnswcE9X";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "WgEraJlXfQ";//애플리케이션 클라이언트 시크릿값";
        StringBuffer response = new StringBuffer();
        JSONObject json;
        try {
            String text = URLEncoder.encode("중고폰", "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/shop?query="+ text + "&sort=date"; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
			json = new JSONObject(br);
            br.close();
        } catch (Exception e) {
        	json = null;
            System.out.println(e);
        }
        return json;
    }
	
	public void save(JSONObject json) {
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

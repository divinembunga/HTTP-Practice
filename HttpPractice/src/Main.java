import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;

public class Main {
	private static HttpURLConnection connection;

	public static void main(String[] args) {
		// Method 1: java.net.httpURLConnection
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try{
			URL url = new URL( "https://jsonplaceholder.typicode.com/albums");
			connection= (HttpURLConnection)url.openConnection();
			
			//Request setup
	        connection.setRequestMethod("GET");
	        connection.setConnectTimeout(5000);
	        connection.setReadTimeout(5000);
	        
	        int status= connection.getResponseCode();
	        //System.out.println(status);
	        if(status >299){
	        	reader = new BufferedReader(new InputStreamReader (connection.getErrorStream()));
	            while((line = reader.readLine()) != null){
	            	responseContent.append(line);
	            }
	            reader.close();
	        }else{
	        	reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            while((line = reader.readLine()) != null){
	            	responseContent.append(line);
	            }
	            reader.close();
	      
	        }
	       // System.out.print(responseContent.toString());
	        parse(responseContent.toString());
		}catch (MalformedURLException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			connection.disconnect();
		}
		
		
	}
	
	public static String parse(String responseBody){
		JSONArray albums = new JSONArray(responseBody);
		for(int i =0; i<1; i++){
			JSONObject album = albums.getJSONObject(i);
			int id= album.getInt("id");
			int userId= album.getInt("userId");
			String title = album.getString("title");
			System.out.println(id+" "+title+" "+userId);
		}
		return null;
	}

}

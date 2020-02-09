import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;

public class Main {
	private static HttpURLConnection connection;
    
	public static void main(String[] args) {
		//Creating the connection
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try{
			URL url = new URL( "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=08c30e02854f612dcb64f5587553");
			connection= (HttpURLConnection)url.openConnection();
			
			//Request setup
	        connection.setRequestMethod("GET");
	        connection.setConnectTimeout(5000);
	        connection.setReadTimeout(5000);
	        
	        int status= connection.getResponseCode();
	        //System.out.println(status);//Test connection
	        if(status >299){
	        	//read the file
	        	reader = new BufferedReader(new InputStreamReader (connection.getErrorStream()));
	            while((line = reader.readLine()) != null){
	            	responseContent.append(line);
	            }
	            reader.close();
	        }else{//read the error message
	        	reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            while((line = reader.readLine()) != null){
	            	responseContent.append(line);
	            }
	            reader.close();
	      
	        }
	      // System.out.print(responseContent.toString());//test
	        parse(responseContent.toString());
		}catch (MalformedURLException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			connection.disconnect();
		}
		
		
	}
	
	public static String parse(String responseBody) throws ParseException{
		//serialising the JSON file
		List<Partner>candidates = new ArrayList<>();
		JSONObject partner = new JSONObject(responseBody);
		JSONArray partners = partner.getJSONArray("partners");
		String[] date= new String[1000];
		
		for(int i =0; i<partners.length(); i++){
			JSONObject list = partners.getJSONObject(i);
			
			String firstName= list.getString("firstName");
			String lastName= list.getString("lastName");
			String email= list.getString("email");
			String country= list.getString("country");
			JSONArray dates = list.getJSONArray("availableDates");
			
			
			for(int j=0; j<dates.length();j++){
				date[j] = dates.getString(j);
				System.out.println(date[j]);
			}
			Partner p= new Partner(firstName,lastName,email,country,date);
			candidates.add(p);
			
			//String firtName= partner.getString("firstName");
			
			//System.out.println(firstName+" "+lastName+" "+email+" "+country);
		      
		}
		compareDates(candidates);
		return null;
	}
	
	public static void compareDates(List<Partner> candidates) throws ParseException{
		boolean dateFound= false;
		int[] popularDate= new int[1000];
		int index=0;
		Map <String, Integer>surveyDates = new HashMap<>();
	    SimpleDateFormat  sdfo = new SimpleDateFormat("yyyy-MM-dd"); 
	    // Get the dates to be compared 
	    Date d1;
	    Date d2;
	    Date d3;
	    Countries[] cs= new Countries[100];
		while( index<candidates.size()){
			Partner p1= candidates.get(index);
			
			String[] d= p1.getDates();
			String[] c = new String[100];
			
			for(int i=1; i<d.length; i++){
				int count=0;
				d1 = sdfo.parse(d[i-1]);
				d3 = sdfo.parse(nextDate(d[i-1]));//ensuring two days in a row
				d2 = sdfo.parse(d[i]);
				if(d2.equals(d3)){
					for(int j=0; j<c.length; j++){
						if(p1.getCountry()!=c[j]){
						    c[j]= p1.getCountry();
							Countries country = new Countries (0, null,p1.getCountry(),null,surveyDates);
							if(country.getSurveyDates().containsKey(d[i-1])){
								int number = country.getSurveyDates().get(d[i-1])+1;
								country.getSurveyDates().put(d[i-1], number);
							}cs[count]= country;
							count++;
						}
					}
				}
					
			}
			
			index++;
			
			
			  
		}
		
		//attempting to find the popular date from the array index and construct
		//the JSON string, but I think I overlooked something and over complicated it
		/*for(int k=0; k< cs.length; k++){
			Countries place = cs[k];
			for(int l=1; l<cs.length; l++){
				if (cs[l-1].getSurveyDates() > (cs[l]){
					
				}
			}
			
		}*/
		
	}
	
	public static String nextDate(String date){ //ex: 2016-01-31 -returns-> 2016-02-01
	    int MMrange = 30;

	    String result = ""; 
	    String daystr = date.substring(8,10);
	    String monthstr = date.substring(5,7);

	    int day = Integer.parseInt(daystr);
	    int month = Integer.parseInt(monthstr);
	    int year = Integer.parseInt(date.substring(0,4));
	    if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) MMrange = 31;
	    else if(month==2) MMrange = 28;
	    if(year%4==0&&month==2) MMrange = 29;
	    if(day==MMrange){
	        day =1;
	        month++;
	    }else if(month==12&&day==31){
	        year++;
	        month = 1;
	        day = 1;
	    }else{
	        day++;
	    }
	    result = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
	    if(month <=9&&day<=9) result = Integer.toString(year)+"-0"+Integer.toString(month)+"-0"+Integer.toString(day);
	    else if(month <= 9) result = Integer.toString(year)+"-0"+Integer.toString(month)+"-"+Integer.toString(day);
	    else if(day <= 9) result = Integer.toString(year)+"-"+Integer.toString(month)+"-0"+Integer.toString(day);
	    return result;
	}

}

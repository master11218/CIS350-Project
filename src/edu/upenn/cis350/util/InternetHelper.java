package edu.upenn.cis350.util;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class InternetHelper {

	/**
	 * Execute a http get request and return the response entity
	 * @param url the http get request including the parameters
	 * @return the entity of the response if it was successful, or an empty string otherwise
	 */
	public static String httpGetRequest(String url){
		HttpClient client = new DefaultHttpClient();
		String result = "";		
		
		try {
			System.out.println("Internet Helper: Making request to "+url);
			HttpGet method = new HttpGet(url);			
			HttpResponse response = client.execute(method);
			
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity);
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}

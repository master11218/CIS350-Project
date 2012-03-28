package edu.upenn.cis350;

public class JsonParseShit {
	
	String url = "http://spectrackulo.us/350/";
	public void test(){
		HttpRequest http = new HttpRequest();
		String result = http.execHttpRequest(url, HttpRequest.HttpMethod.Get, null);
		parse(result);	
	}
	
	public void parse(String json){
		
	}
}

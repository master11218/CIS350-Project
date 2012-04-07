package edu.upenn.cis350.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;


//Internal base deal request object - makes actual HTTP Request (GET/POST)
public class HttpRequest {
	
	// Internal Http Method Enumeration
	public enum HttpMethod {
		Get,
		Post
	};
	
	protected HttpClient m_client;
	
	public HttpRequest() {
		
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
		 
		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		 
		ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);
		m_client = new DefaultHttpClient(cm, params);
	}
	
	//protected
	public HttpRequest(HttpClient client) {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
		 
		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		 
		ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);
		m_client = new DefaultHttpClient(cm, params);
	}		
	//protected
	public String execHttpRequest(String uri, HttpMethod method, String body) {	
		HttpRequestBase request;
		
		if (method == HttpMethod.Get)
			request = new HttpGet();
		else if (method == HttpMethod.Post)
			request = new HttpPost();
		else
			return null;

		try {			
			request.setURI(new URI(uri.toString()));
			
			// Set Headers
            request.addHeader("User-Agent", "YP Mobile Android/2.1");
            
            // Execute
            HttpResponse response = m_client.execute(request);
        
            // Get Response Text
            InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
            BufferedReader in = new BufferedReader(isr, 8192);
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            isr.close();
            String respText = sb.toString();
            return respText.trim();
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	//protected
	public byte[] execBinaryHttpRequest(String uri, HttpMethod method, String body) {	
		HttpRequestBase request;
		
		if (method == HttpMethod.Get)
			request = new HttpGet();
		else if (method == HttpMethod.Post)
			request = new HttpPost();
		else
			return null;

		try {			
			request.setURI(new URI(uri.toString()));
			
			// Set Headers
            request.addHeader("User-Agent", "YP Mobile Android/2.1");
            
            // Execute
            HttpResponse response = m_client.execute(request);
        
            // Get Response Text
            return EntityUtils.toByteArray(response.getEntity());
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}
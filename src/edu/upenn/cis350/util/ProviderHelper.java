package edu.upenn.cis350.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.upenn.cis350.Provider;
import edu.upenn.cis350.Rating;

public class ProviderHelper {

	
	private static final String DATABASE_SITE = "http://spectrackulo.us/350/";

	
	/**
	 * This is a helper method for the search
	 * It will return a list of satisfied providers
	 * @param provider_name
	 * @param has_parking
	 * @param accepting_new
	 * @param handicap
	 * @param appointment_only
	 * @param credit_card
	 * @param type
	 * @param distance
	 * @param m_longitude 
	 * @param m_latitude 
	 * @return the list of satisfied provider, or an empty list if no provider satisfied the requirement
	 */
	public static ArrayList<Provider> getSatisfiedProvider(String provider_name, String has_parking, 
			String accepting_new, String handicap, String appointment_only, String credit_card, 
			String type, String distance, Double m_latitude, Double m_longitude) {
		
		ArrayList<Provider> allproviders = new ArrayList<Provider>();
		StringBuffer urlBuff = new StringBuffer();
		urlBuff.append(ProviderHelper.DATABASE_SITE);
		urlBuff.append("?");
		
		//Build the url for the get request
		if (provider_name.length()>0) urlBuff.append("name="+provider_name);
		if (!has_parking.equals("doesn't matter")) urlBuff.append("&has_parking="+has_parking);
		if (!accepting_new.equals("doesn't matter")) urlBuff.append("&accepting_new="+accepting_new);
		if (!handicap.equals("doesn't matter")) urlBuff.append("&handicap="+handicap);		
		if (!appointment_only.equals("doesn't matter")) urlBuff.append("&appointment_only="+appointment_only);		
		if (!credit_card.equals("doesn't matter")) urlBuff.append("&credit_card="+credit_card);
		if (!type.equals("doesn't matter")) {
			//Need to convert the naming here
			urlBuff.append(type.equals("primary care")? "&type=PCP":"&type=Specialist");
		}
		if (distance.length()>0){
			urlBuff.append("&distance="+distance+"&long="+m_longitude+"&lat="+m_latitude);
		}
		
		//Send the request
		String jsonString = InternetHelper.httpGetRequest(urlBuff.toString());
		
		System.out.println(urlBuff.toString());
		System.out.println(jsonString);
		
		if (jsonString!=null && jsonString.length()>0){
			try{
				JSONObject jsonobj = new JSONObject(jsonString);
				JSONArray providers = jsonobj.getJSONArray("providers");
				
				for(int i=0;i < providers.length();i++){						
					JSONObject json = providers.getJSONObject(i);
					
					Provider currentProvider = new Provider(Long.parseLong(json.getString("pid")), json.getString("name"), json.getString("address"), 
							json.getString("city"), json.getString("state"), json.getString("zip"), json.getString("phone"),
							json.getString("accepting_new"), json.getString("has_parking"),
							json.getString("type"), json.getString("credit_cards"), json.getString("handicap_access"),
							json.getString("appointment_only"), Double.parseDouble(json.getString("average_rating")),
							Double.parseDouble(json.getString("longitude")), Double.parseDouble(json.getString("latitude")), json.getString("website"), json.getString("hours"));
					
					allproviders.add(currentProvider);
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return allproviders;
	}
}

package edu.upenn.cis350.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.upenn.cis350.entities.Provider;

/**
 * This is a helper class that provides all the tool needed for the provider
 * object
 * 
 * @author henryou
 * 
 */
public class ProviderHelper {
	
	private static final String DATABASE_SITE = "http://spectrackulo.us/350/";

	
	/**
	 * This is a helper method for the search It will return a list of satisfied
	 * providers according to the search criteria by making a request to the backend database
	 * 
	 * @param provider_name The name of the provider
	 * @param has_parking whether the provider facility has parking
	 * @param accepting_new whether the provider accepts new patients
	 * @param handicap Handicap facility available
	 * @param appointment_only Is the provider appointment only
	 * @param credit_card Accepts credit card or not
	 * @param type Primary Care or Specialist
	 * @param distance 
	 * @param m_longitude
	 * @param m_latitude
	 * @return the list of satisfied provider, or an empty list if no provider
	 *         satisfied the requirement
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
		
//		System.out.println(urlBuff.toString());
//		System.out.println(jsonString);
		
		if (jsonString!=null && jsonString.length()>0){
			allproviders = ProviderHelper.createListOfProvidersFromJson(jsonString);
		}
		
		return allproviders;
	}
	
	
	/**
	 * Create a Provider object from the Json string 
	 * @param json the json object of a provider
	 * @return a provider object if successful, or null if unsuccessful
	 */
	public static Provider createProviderFromJson(JSONObject json){
		Provider provider = null;
		
		try{
			long pid = Long.parseLong(json.getString("pid"));
			String name = json.getString("name");
			String address = json.getString("address");
			String city = json.getString("city");
			String state = json.getString("state");
			String zip = json.getString("zip");
			String phone = json.getString("phone");
			String accepting_new = json.getString("accepting_new");
			String has_parking = json.getString("has_parking");
			String type = json.getString("type");
			String credit_cards = json.getString("credit_cards");
			String handicap_access = json.getString("handicap_access");
			String appointment_only = json.getString("appointment_only");
			Double average_rating = Double.parseDouble(json
					.getString("average_rating"));
			Double longitude = Double.parseDouble(json.getString("longitude"));
			Double latitude = Double.parseDouble(json.getString("latitude"));
			String website = json.getString("website");
			String hours = json.getString("hours");
		
			provider = new Provider(pid, name, address, city, state, zip,
					phone, accepting_new, has_parking, type, credit_cards,
					handicap_access, appointment_only, average_rating,
					longitude, latitude, website, hours);

		}catch(Exception e){
			//when there were exception parsing the method or errors in the json string
			System.out.println("Error parsing the json to provider object");
		}
		
		return provider;
	}
	
	
	/**
	 * Create an array of providers object from a json string
	 * @param jsonString The json string to be parsed
	 * @return the arraylist of provider if successful, or an empty list if no provider is in the jsonobj
	 */
	public static ArrayList<Provider> createListOfProvidersFromJson(String jsonString){
		ArrayList<Provider> allproviders = new ArrayList<Provider>();
		
		//Parse the json string
		try{
			JSONObject jsonobj = new JSONObject(jsonString);
			JSONArray providers = jsonobj.getJSONArray("providers");
			
			for(int i=0;i < providers.length();i++){						
				JSONObject json = providers.getJSONObject(i);	
				Provider currentProvider = ProviderHelper.createProviderFromJson(json);
				if (currentProvider != null){
					allproviders.add(currentProvider);
				}
			}
		} catch (Exception e){
			System.out.println("Error parsing the json string");
		}
		
		return allproviders;
	}
	
}

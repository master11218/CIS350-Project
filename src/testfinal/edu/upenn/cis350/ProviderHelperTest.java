package testfinal.edu.upenn.cis350;


import org.json.JSONException;
import org.json.JSONObject;

import edu.upenn.cis350.util.*;
import edu.upenn.cis350.entities.*;

import junit.framework.TestCase;

public class ProviderHelperTest extends TestCase {

	public void testJsonParsing() throws JSONException{
		String jsonstring = "{\"pid\":\"1\",\"name\":\"Tanvir Ahmed\",\"address\":\"3909 Spruce Street\",\"city\":\"Philadelphia\",\"state\":\"PA\",\"zip\":\"19104\",\"phone\":\"(561) 207-1086\",\"accepting_new\":\"no\",\"has_parking\":\"yes\",\"type\":\"PCP\",\"latitude\":\"39.951481\",\"longitude\":\"-75.200989\",\"credit_cards\":\"yes\",\"handicap_access\":\"no\",\"appointment_only\":\"yes\",\"website\":\"http:\\/\\/www.google.com\",\"hours\":\"Mondays - Fridays from 9am to 5pm\",\"average_rating\":\"3.80\"}";
		JSONObject tempJson = new JSONObject(jsonstring);
		
		Provider testProvider = ProviderHelper.createProviderFromJson(tempJson);
		
		assertEquals(testProvider.getID(), 1);
		assertEquals(testProvider.getName(),"Tanvir Ahmed");
		
	}
	
}


package testfinal.edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.*;
import edu.upenn.cis350.*;
import edu.upenn.cis350.entities.Provider;
import edu.upenn.cis350.entities.Rating;

public class ProviderTest extends ActivityInstrumentationTestCase2<ProviderProfileActivity> {
	
	public ProviderTest() {
		super("edu.upenn.cis350", ProviderProfileActivity.class);
	}
	
	private Activity activity;
	private Button buttonmap;
	private TextView name;
	private TextView address;
	private TextView phone;
	private Button rate;
	private TextView rating;
	private Provider m_provider;
	
	public Provider generateProvider(String name, double latitude,
			double longitude) {
		Rating first = new Rating(
				3,
				1,
				"",
				"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
				5);
		Rating second = new Rating(
				4,
				1,
				"",
				" adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehend",
				3);
		Rating third = new Rating(5, 1, "",
				" This guy is awesome!!!!!!!!!!!!!!!!!!!!!!!", 3);
		Rating fourth = new Rating(5, 1, "", " I don't speak latin );", 4);
		ArrayList<Rating> ratings = new ArrayList<Rating>();

		ratings.add(first);
		ratings.add(second);
		ratings.add(third);
		ratings.add(fourth);

		// initialize a dummy provider.
		return new Provider(1, name, "3400 Spruce Street", "Philadelphia",
				"PA", "19104", "(215)662-3228", "yes", "yes", "PCP", "yes",
				"yes", "yes", 3, longitude, latitude, "None", "stfu");
	}

	
	public void setUp() throws Exception {
		super.setUp();
		m_provider = generateProvider("testProvider", 1.300, 2.04);
		activity= getActivity();
		buttonmap= (Button)activity.findViewById(R.id.button_providerpf_map);
		name= (TextView)activity.findViewById(R.id.provider_name);
		address= (TextView)activity.findViewById(R.id.provider_address);
		phone= (TextView)activity.findViewById(R.id.provider_phone);
		rating= (TextView)activity.findViewById(R.id.providerpf_average_rating_text);
		rate= (Button)activity.findViewById(R.id.providerpf_rate_button);
	}
	//tests to make sure everything displays correctly
	public void testButtonMap() {
		assertEquals(name, "testProvider");
	}
	public void testName() {
		assertNotNull(buttonmap);
	}
	public void testPhone() {
		assertNotNull(phone);
	}
	public void testRating() {
		assertNotNull(rating);
	}
	public void testRate() {
		assertNotNull(rate);
	}
	

}

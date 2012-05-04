package testfinal.edu.upenn.cis350;

import java.util.ArrayList;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.*;
import edu.upenn.cis350.*;
import edu.upenn.cis350.entities.Provider;
import edu.upenn.cis350.entities.Rating;


public class SearchTest extends ActivityInstrumentationTestCase2<SearchActivity> {
	public SearchTest() {
		super("edu.upenn.cis350", SearchActivity.class);
	}
	
	private Activity activity;
	private TextView title;
	private EditText input;
	private Button search;
	private Button filter;
	private ArrayList<Provider> _providers = new ArrayList<Provider>();
	
	public void setUp() throws Exception {
		super.setUp();
		activity= getActivity();
		search= (Button)activity.findViewById(R.id.search_button);
		input = (EditText)activity.findViewById(R.id.search_distance);
	}
	
	public void testSearchButton() {
		assertEquals(search.getText(), "Search");
	}
	public void testTitle() {
		assertEquals(title.getText(), "Search for Provider");
	}
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

	public void generateProviderList() {

		// Generate a bunch of temporary random providers
		_providers.add(generateProvider("Loraine Zachery", 39.951481,
				-75.180987));
		_providers.add(generateProvider("Tyrone Bolan", 39.952481, -75.190987));
		_providers
				.add(generateProvider("Roslyn Chico", 39.9512376, -75.100987));
		_providers
				.add(generateProvider("Sharron Becher", 39.941237, -75.211987));
		_providers
				.add(generateProvider("Steidl Zachery", 39.912731, -75.223421));
		_providers.add(generateProvider("Hugh Tandy", 39.91111, -75.0843728));
		_providers.add(generateProvider("Mathew Dimas", 39.957481, -75.200987));
		_providers.add(generateProvider("Milagros Siegmund", 39.958481,
				-75.178178));
		_providers.add(generateProvider("Serena Champine", 39.959481,
				-75.192789));
		_providers.add(generateProvider("Allie Lunday", 39.950481, -75.188687));
	}
}

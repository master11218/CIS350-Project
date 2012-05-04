package testfinal.edu.upenn.cis350;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.*;
import edu.upenn.cis350.*;


public class SearchTest extends ActivityInstrumentationTestCase2<SearchActivity> {
	public SearchTest() {
		super("edu.upenn.cis350", SearchActivity.class);
	}
	
	private Activity activity;
	private TextView title;
	private EditText input;
	private Button search;
	private Spinner parking;
	
	public void setUp() throws Exception {
		super.setUp();
		activity= getActivity();
		search= (Button)activity.findViewById(R.id.search_button);
		parking= (Spinner)activity.findViewById(R.id.parking_spinner);
		
		
		
	}
	
	public void testParkingSpinner() {
		assertEquals(parking.getAdapter().getCount(), 2);
	}
	
	public void testSearchButton() {
		assertEquals(search.getText(), "Search");
	}
	
	
	public void testTitle() {
		assertEquals(title.getText(), "Search for Provider");
	}
	
}

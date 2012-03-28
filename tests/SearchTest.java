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
	private Button filter;
	
	public void setUp() throws Exception {
		super.setUp();
		activity= getActivity();
		search= (Button)activity.findViewById(R.id.search_submit_button);
		filter= (Button)activity.findViewById(R.id.search_filter_button);
		input = (EditText)activity.findViewById(R.id.search_submit);
		title = (TextView)activity.findViewById(R.id.search_title);
		
	}
	
	public void testSearchButton() {
		assertEquals(search.getText(), "Search");
	}
	public void testFilterButton() {
		assertEquals(filter.getText(), "Filter");
	}
	
	public void testTitle() {
		assertEquals(title.getText(), "Search for Provider");
	}
	
}

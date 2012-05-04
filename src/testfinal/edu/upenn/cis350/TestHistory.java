package testfinal.edu.upenn.cis350;
import edu.upenn.cis350.*;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Populating the history of a user's interactions with the application requires many simulations and hence is not very testable
 * @author DXU
 *
 */
public class TestHistory extends ActivityInstrumentationTestCase2<HistoryActivity> {
	
	private Activity activity;
	private ScrollView sv;
	
	
	public TestHistory() {
		super("edu.upenn.cis350", HistoryActivity.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		activity= getActivity();
		sv = (ScrollView)activity.findViewById(R.id.scrollView1);
		
	}
	
	public void testHistory() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		//wait for UI Thread to finish
		getInstrumentation().waitForIdleSync();

		assertNotNull(sv);

		

		
	}
	
}

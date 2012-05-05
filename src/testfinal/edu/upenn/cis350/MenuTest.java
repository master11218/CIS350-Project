package testfinal.edu.upenn.cis350;

import edu.upenn.cis350.*;
import edu.upenn.cis350.R;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;


public class MenuTest extends ActivityInstrumentationTestCase2<VoiceActivity> {
	
	
	public MenuTest() {
		super("edu.upenn.cis350", VoiceActivity.class);
	}
	
	private Activity activity;
	private Activity tempactivity;
	private TextView sample;
	private Button button;
	private Button searchbutton;
	private Button historybutton;
	private Button profilebutton;
	private Button contactbutton;
	private Button mapbutton;
	
	
	public void setUp() throws Exception {
		super.setUp();
		activity= getActivity();
		searchbutton= (Button)activity.findViewById(R.id.home_btn_search);
		historybutton= (Button)activity.findViewById(R.id.home_btn_history);
		profilebutton= (Button)activity.findViewById(R.id.home_btn_profile);
		contactbutton= (Button)activity.findViewById(R.id.home_btn_contact);
		mapbutton= (Button)activity.findViewById(R.id.home_btn_map);
	}
	
	public void testSearch() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		//wait for UI Thread to finish
		getInstrumentation().waitForIdleSync();

		assertEquals(searchbutton.getText(), "search");

		

		
	}
	public void testHistory() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		//wait for UI Thread to finish
		getInstrumentation().waitForIdleSync();
		assertEquals(historybutton.getText(), "history");
	}
	public void testProfile() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		//wait for UI Thread to finish
		getInstrumentation().waitForIdleSync();
		assertEquals(profilebutton.getText(), "profile");
	}
	public void testContact() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		//wait for UI Thread to finish
		getInstrumentation().waitForIdleSync();
		assertEquals(contactbutton.getText(), "contact us");
	}
	public void testMap() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});
		//wait for UI Thread to finish
		getInstrumentation().waitForIdleSync();
		assertEquals(mapbutton.getText(), "map");
	}
}

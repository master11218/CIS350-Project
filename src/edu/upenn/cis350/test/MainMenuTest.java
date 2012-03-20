package edu.upenn.cis350.test;

import edu.upenn.cis350.*;
import android.R;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class MainMenuTest extends ActivityInstrumentationTestCase2<VoiceActivity> {

	public MainMenuTest() {
		super("edu.upenn.cis350", VoiceActivity.class);
	}

	// fields used in the testing
	private Activity activity;
	private Button button;

	// called before each test
	public void setUp() throws Exception { 
	    super.setUp();
	    activity = getActivity();
	    //button = (Button)activity.findViewById(edu.upenn.cis350.R.id.button_search);
	}


	// test what happens when you click twice
	public void testSearchButton() {
		
		System.out.println("Testing...");
		
		assertNull(button);
		/*
		// put this in the UI Thread
		activity.runOnUiThread(new Runnable() {
		public void run() {
			button.performClick();
		}
		});

		
		
		// wait for UI Thread to finish
		getInstrumentation().waitForIdleSync();

		Activity currActivity = getActivity();
		
		*/
	}	
	
}

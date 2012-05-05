package testfinal.edu.upenn.cis350;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import edu.upenn.cis350.*;


public class ProfileTest extends ActivityInstrumentationTestCase2<ProfileActivity> {
	public ProfileTest() {
		super("edu.upenn.cis350", ProfileActivity.class);
	}
	private Activity activity;

	private TextView name;
	private TextView address;
	private TextView email;
	private TextView phone;
	
	public void setUp() throws Exception {
		super.setUp();
		activity= getActivity();
		name= (EditText)activity.findViewById(R.id.profile_new_name);
		address= (EditText)activity.findViewById(R.id.profile_new_address);
		email= (EditText)activity.findViewById(R.id.profile_new_email);
		phone= (EditText)activity.findViewById(R.id.profile_new_phone);
		
	}
	public void testActivity() {
		assertNotNull(activity);
	}
	public void testName() {
		assertNotNull(name);
	}
	public void testAddress() {
		assertNotNull(address);
	}
	public void testEmail() {
		assertNotNull(email);
	}
	public void testPhone() {
		assertNotNull(phone);
	}

	
}

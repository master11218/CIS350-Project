import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import edu.upenn.cis350.*;

public class ContactTest extends ActivityInstrumentationTestCase2<ContactActivity> {
	public ContactTest() {
		super("edu.upenn.cis350", ContactActivity.class);
	}
	
	private Activity activity;
	private TextView copyright;
	private TextView more;
	private TextView info_name;
	private TextView info_email;
	private TextView info_number;
	
	public void setUp() throws Exception {
		super.setUp();
		activity= getActivity();
		copyright= (TextView) activity.findViewById(R.id.contact_copyright);
		more= (TextView) activity.findViewById(R.id.contact_more);
		info_name= (TextView) activity.findViewById(R.id.contact_info_name);
		info_email= (TextView) activity.findViewById(R.id.contact_info_email);
		info_number= (TextView) activity.findViewById(R.id.contact_info_number);
	}
	
	public void testCopyright() {
		assertEquals(copyright.getText(), "ProviderPlus is an app provided by the University of Pennsylvania School of Nursing");
	}
	public void testMore() {
		assertEquals(more.getText(), "For more information, please contact:");
	}
	public void testinfo_name() {
		assertEquals(info_name.getText(), "Evan Wu");
	}
	public void testinfo_email() {
		assertEquals(info_email.getText(), "evanswu@nursing.seas.upenn.edu");
	}
	public void testinfo_number() {
		assertEquals(info_number.getText(), "1-215-255-2555");
	}
	
	
}

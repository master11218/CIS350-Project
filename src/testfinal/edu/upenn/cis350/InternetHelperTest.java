package testfinal.edu.upenn.cis350;

import org.apache.http.client.HttpClient;

import edu.upenn.cis350.ContactActivity;
import edu.upenn.cis350.util.InternetHelper;
import junit.framework.TestCase;

/**
 * This class' methods cannot truly be tested since the provided URL is dynamic
 * @author DXU
 *
 */
public class InternetHelperTest extends TestCase {
	
	public String url;
	public HttpClient client;
	public String result;
	
	public InternetHelperTest() {
		super();
	}
	
	public void setUp() throws Exception {
		super.setUp();

	}

}

package testfinal.edu.upenn.cis350;

import com.google.android.maps.GeoPoint;

import android.graphics.Color;
import edu.upenn.cis350.RouteOverlay;
import junit.framework.TestCase;

public class RouteOverlayTest extends TestCase {
	
	public RouteOverlay overlay;
	
	public RouteOverlayTest() {
		super();
	}
	
	public void setUp() throws Exception {
		
		
	}
	
	public void testOverlay() {
		overlay= new RouteOverlay(new GeoPoint(1, 1), new GeoPoint(2, 2), Color.RED);
		assertNotNull(overlay);
	}

}

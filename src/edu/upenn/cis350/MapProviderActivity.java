/*
 * I realized after coding the class that we need to be passing the Provider object around.
 * I'll rewrite the class to deal with multiple providers once there is code that supports it.
 */

package edu.upenn.cis350;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MapProviderActivity extends MapActivity{
	private MapView _myMapView;
	private MapController _myMapController;
	private ArrayList<GeoPoint> _points;
	private ArrayList<String> _descriptions;
	
	public MapProviderActivity(){
		_points = new ArrayList<GeoPoint>();
		_descriptions = new ArrayList<String>();
		
		//default point if nothing specified
		_points.add(new GeoPoint(39950446,-75192587));
		_descriptions.add("Abbasi, Nadeem Ahmed, MD");
		
		/*
		 * Code to get all points within a certain radius goes here.
		 * Cannot be implemented without a dataset.
		 */
	}
	
	public MapProviderActivity(ArrayList<GeoPoint> points, ArrayList<String> descriptions){
		_points = points;
		_descriptions = descriptions;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		_myMapView = (MapView) findViewById(R.id.mapview);
		_myMapController = _myMapView.getController();
		_myMapController.setZoom(15);
		_myMapView.setBuiltInZoomControls(true);

		//center the map to Penn
		GeoPoint pennLocation = new GeoPoint(39951481, -75200987);
		_myMapController.animateTo(pennLocation);
		
		//add additional "pins" to the map
		List<Overlay> mapOverlays = _myMapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, this);
		
		for(int i = 0; i < _points.size(); i++){
			OverlayItem overlayitem = new OverlayItem(_points.get(i), _descriptions.get(i), "");
			itemizedoverlay.addOverlay(overlayitem);
		}

		mapOverlays.add(itemizedoverlay);
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}

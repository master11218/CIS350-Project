package edu.upenn.cis350;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.upenn.cis350.MyLocation.LocationResult;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MapProviderActivity extends MapActivity{
	private MapView _myMapView;
	private MapController _myMapController;
	private ArrayList<Provider> _providers;
	private Double m_lat;
	private Double m_long;
	private Context m_context = this;


	MyLocation m_location = new MyLocation();
	private void locationClick() {
		m_location.getLocation(this, locationResult);
	}

	public LocationResult locationResult = new LocationResult(){
		@Override
		public void gotLocation(Location location){
			if(location != null){
				m_lat = location.getLatitude();
				m_long = location.getLongitude();
				Toast.makeText(m_context, "The lat: " + m_lat.toString() + " The long: " + m_long.toString(), Toast.LENGTH_SHORT).show();
				
			}
			//Got the location!
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		_descriptions.add("Abbasi, Nadeem Ahmed, MD");
		//grab the location, set the latitude and longitude
		locationClick();
		
		_providers = new ArrayList<Provider>();
		_providers.add((Provider)getIntent().getSerializableExtra("providers"));
		if(_providers.size() == 1)
			System.out.println("Would have shown directions");

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
		itemizedoverlay.setProviders(_providers);
		
		for(int i = 0; i < _providers.size(); i++){
			GeoPoint p = new GeoPoint((int)(_providers.get(i).getLongitude() * 1000000), (int)(_providers.get(i).getLatitude()*1000000));
			OverlayItem overlayitem = new OverlayItem(p, "", "");
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

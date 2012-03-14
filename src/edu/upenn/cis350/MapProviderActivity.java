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
import java.util.Date;
import java.util.List;

public class MapProviderActivity extends MapActivity{
	private MapView _myMapView;
	private MapController _myMapController;
	private ArrayList<Provider> _providers = new ArrayList<Provider>();;
	private Double m_lat;
	private Double m_long;
	private Context m_context = this;

	//For temporary shit
	

	public Provider generateProvider(String name, double latitude, double longitude){
		Rating first = new Rating(3,1,new Date(System.currentTimeMillis()), "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
				5);
		Rating second = new Rating(4,1,new Date(System.currentTimeMillis()), " adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehend",
				3);
		Rating third = new Rating(5,1,new Date(System.currentTimeMillis()), " This guy is awesome!!!!!!!!!!!!!!!!!!!!!!!",
				3);
		Rating fourth= new Rating(5,1,new Date(System.currentTimeMillis()), " I don't speak latin );",
				4);
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		ratings.add(first);
		ratings.add(second);
		ratings.add(third);
		ratings.add(fourth);
		
		//initialize a dummy provider.
		return new Provider(1, name, "3400 Spruce Street, 8 Ravdin, Philadelphia, PA", "(215)662-3228", ratings, latitude, longitude);
	}
	
	public void generateProviderList(){

        //Generate a bunch of temporary random providers
        _providers.add(generateProvider("Loraine Zachery", 39.951481, -75.180987));
        _providers.add(generateProvider("Tyrone Bolan", 39.952481, -75.190987));
        _providers.add(generateProvider("Roslyn Chico", 39.9512376, -75.100987));
        _providers.add(generateProvider("Sharron Becher", 39.941237, -75.211987));
        _providers.add(generateProvider("Steidl Zachery", 39.912731, -75.223421));
        _providers.add(generateProvider("Hugh Tandy", 39.91111, -75.0843728));
        _providers.add(generateProvider("Mathew Dimas", 39.957481, -75.200987));
        _providers.add(generateProvider("Milagros Siegmund", 39.958481, -75.178178));
        _providers.add(generateProvider("Serena Champine", 39.959481, -75.192789));
        _providers.add(generateProvider("Allie Lunday", 39.950481, -75.188687));
	}
	
	
	//actual code

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
				Toast.makeText(m_context, "Your current location is (" + m_lat.toString() + ", " + m_long.toString() + ")", Toast.LENGTH_SHORT).show();
				
			}
			//Got the location!
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//grab the location, set the latitude and longitude
		locationClick();
		
		Provider intentProvider = (Provider)getIntent().getSerializableExtra("providers");
		if(intentProvider == null)
			generateProviderList();
		else
			_providers.add(intentProvider);
		if(_providers.size() == 1){
			System.out.println("Would have shown directions");
		}

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

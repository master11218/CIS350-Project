package edu.upenn.cis350;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.upenn.cis350.MyLocation.LocationResult;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
	private Float m_lat;
	private Float m_long;
	private Context m_context = this;
	private SharedPreferences settings;
	//gloabl variable mapOverlays so that we can add personal location after we've received the location
	private List<Overlay> mapOverlays;
	private ProgressDialog m_loading_dialog;

	//actual code
	MyLocation m_location = new MyLocation();
	private void locationClick() {
		System.out.println("HIT LOCATION CLICK");
		m_location.getLocation(this, locationResult);
	}
	
	public void displayCurrentLocationOnMap(){
		//add your current location pin to the map
		Drawable current_location_drawable = this.getResources().getDrawable(R.drawable.current_location_marker);
		MapItemizedOverlay personalLocationOverlay = new MapItemizedOverlay(current_location_drawable, this);
		//you as a person will be identified as a dummy provider, with a null ratings. 
		Provider personal = new Provider(1, "adsf", "3400 Spruce Street", "Philadelphia", "PA", "19104", "(215)662-3228", 
				"yes", "yes", "PCP", "yes", "yes",
				"yes", 3, 
				1.1, 1.1, "None", "stfu");
		//create an arraylist just containing this to pass to the mapitemized overlay
		ArrayList<Provider> personal_templist = new ArrayList<Provider>();
		personalLocationOverlay.setProviders(personal_templist);
		//create a geopoint and overlay item for yourself.
		GeoPoint p = new GeoPoint((int)(m_lat * 1000000), (int)(m_long * 1000000));
		OverlayItem overlayitem = new OverlayItem(p, "", "");
		personalLocationOverlay.addOverlay(overlayitem);
		
		//add yourself
		mapOverlays.add(personalLocationOverlay);
		

		System.out.println("NEW MAPOVERLAY ADDED< SHOULD'VE RESET.");
	}

	public LocationResult locationResult = new LocationResult(){
		@Override
		public void gotLocation(Location location){
			if(location != null){
				System.out.println("INSIDE GOT LOCATION");
				m_lat = (float)location.getLatitude();
				m_long = (float)location.getLongitude();
				
				//Save your location in the User info is set in the shared preferences.
				SharedPreferences.Editor editor = settings.edit();
				editor.putFloat("latitude", m_lat);
				editor.putFloat("longitude", m_long);
				editor.commit();
				
				//debug   + "Your current location is (" + m_lat.toString() + ", " + m_long.toString() + ")"
				//Toast.makeText(m_context, "Your location has been saved. ", Toast.LENGTH_SHORT).show();
				
				System.out.println("CURRENT LOCATION WAS GOTTEN, SHOULD'VE DISPLAYED TOAST");
				
				//displayyourself on map
				displayCurrentLocationOnMap();
				
			}
			//Got the location!

		}
	};
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		settings = getSharedPreferences("UserData", 0);
		m_loading_dialog = ProgressDialog.show(MapProviderActivity.this, "", 
	            "Finding your current location. Please wait...", true);
		
		System.out.println("JUST PRINTED THE LOADING DIALOG");
		//grab the location, set the latitude and longitude
		locationClick();
		
		//grab the providers from the previous activity, if there are any.
		Provider intentProvider = (Provider)getIntent().getSerializableExtra("providers");
		if(intentProvider == null)
			generateProviderList();
		else
			_providers.add(intentProvider);
		if(_providers.size() == 1){
			System.out.println("Would have shown directions");
		}

		_myMapView = (MapView) findViewById(R.id.mapview);
		_myMapController = _myMapView.getController();
		_myMapController.setZoom(15);
		_myMapView.setBuiltInZoomControls(true);

		//center the map to Penn
		GeoPoint pennLocation = new GeoPoint(39951481, -75200987);
		_myMapController.animateTo(pennLocation);
		
		//add additional "pins" to the map
		mapOverlays = _myMapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.current_location_marker_bw);
		MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, this);
		itemizedoverlay.setProviders(_providers);
		
		for(int i = 0; i < _providers.size(); i++){
			GeoPoint p = new GeoPoint((int)(_providers.get(i).getLongitude() * 1000000), (int)(_providers.get(i).getLatitude()*1000000));
			OverlayItem overlayitem = new OverlayItem(p, "", "");
			itemizedoverlay.addOverlay(overlayitem);
		}
		
		//adding yourself comes after the location has been received.
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
	
	
	
	

	//For temporary shit
	public Provider generateProvider(String name, double latitude, double longitude){
		Rating first = new Rating(3,1,"", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
				5);
		Rating second = new Rating(4,1,"", " adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehend",
				3);
		Rating third = new Rating(5,1,"", " This guy is awesome!!!!!!!!!!!!!!!!!!!!!!!",
				3);
		Rating fourth= new Rating(5,1,"", " I don't speak latin );",
				4);
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		ratings.add(first);
		ratings.add(second);
		ratings.add(third);
		ratings.add(fourth);
		
		//initialize a dummy provider.
		return new Provider(1, name, "3400 Spruce Street", "Philadelphia", "PA", "19104", "(215)662-3228", 
				"yes", "yes", "PCP", "yes", "yes",
				"yes", 3, 
				longitude, latitude, "None", "stfu");
		
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
	
}

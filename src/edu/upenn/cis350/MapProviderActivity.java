package edu.upenn.cis350;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.upenn.cis350.util.InternetHelper;
import edu.upenn.cis350.util.MyLocation;
import edu.upenn.cis350.util.ProviderHelper;
import edu.upenn.cis350.util.MyLocation.LocationResult;
import edu.upenn.cis350.entities.Provider;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/**
 * A map view that display the provider around the user location.
 * 
 */
public class MapProviderActivity extends MapActivity {
	private MapView _myMapView;
	private MapController _myMapController;
	private ArrayList<Provider> _providers = new ArrayList<Provider>();;
	private Float m_lat;
	private Float m_long;
	private SharedPreferences settings;
	// global variable mapOverlays so that we can add personal location after
	// we've received the location
	private List<Overlay> mapOverlays;
	private ProgressDialog m_loading_dialog;
	private GeoPoint m_current_location;
	private List<GeoPoint> m_pathList;
	
	private Integer pennLatitude = 39951481;
	private Integer pennLongitude = -75200987;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		settings = getSharedPreferences("UserData", 0);
		// set the loading screen. This will get destroyed when location is
		// found via locationResult
		m_loading_dialog = ProgressDialog.show(MapProviderActivity.this, "",
				"Finding your current location. Please wait...", true);

		// start location search
		// LocationClick() will cause location result to run when finished in
		// thread.
		locationClick();
		// set up map
		_myMapView = (MapView) findViewById(R.id.mapview);
		_myMapController = _myMapView.getController();
		_myMapController.setZoom(15);
		_myMapView.setBuiltInZoomControls(true);
		mapOverlays = _myMapView.getOverlays();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	// used grab current location in background thread
	MyLocation m_location = new MyLocation();
	private void locationClick() {
		m_location.getLocation(this, locationResult);
	}
	public LocationResult locationResult = new LocationResult() {
		@Override
		public void gotLocation(Location location) {
			if (location != null) {
				m_lat = (float) location.getLatitude();
				m_long = (float) location.getLongitude();
				// Save your location in the User info is set in the shared
				// preferences.
				SharedPreferences.Editor editor = settings.edit();
				editor.putFloat("latitude", m_lat);
				editor.putFloat("longitude", m_long);
				editor.commit();
				// Set the current location in this activity
				m_current_location = new GeoPoint(
						(int) (1000000 * location.getLatitude()),
						(int) (1000000 * location.getLongitude()));

				displayAllProvidersOnMap();
				displayCurrentLocationOnMap();
				// center to yourself
				_myMapController.animateTo(m_current_location);

				// refresh the map
				_myMapView.invalidate();
			}
		}
	};

	public void displayCurrentLocationOnMap() {
		// add your current location pin to the map
		Drawable current_location_drawable = this.getResources().getDrawable(
				R.drawable.current_location_marker);
		MapItemizedOverlay personalLocationOverlay = new MapItemizedOverlay(
				current_location_drawable, this);
		// you as a person will be identified as a dummy provider, with a null
		// ratings.
		Provider personal = new Provider(1, "Your Current Location", "You are located near",
				"", "", "", "(" + m_lat.toString() + "," + m_long.toString() + ")", "", "",
				"", "", "", "", -1, 0, 0, "", "");
		// create an array list just containing this to pass to the map itemized
		// overlay
		ArrayList<Provider> personal_templist = new ArrayList<Provider>();
		personal_templist.add(personal);
		System.out.println(personal.toString());
		personalLocationOverlay.setProviders(personal_templist);
		// create a geo-point and overlay item for yourself.
		GeoPoint p = new GeoPoint((int) (m_lat * 1000000),
				(int) (m_long * 1000000));
		OverlayItem overlayitem = new OverlayItem(p, "", "");
		personalLocationOverlay.addOverlay(overlayitem);

		// add yourself
		mapOverlays.add(personalLocationOverlay);
		

		// if only one provider, draw the path.

		if (_providers.size() == 1) {
			double temp_latitude = _providers.get(0).getLatitude();
			double temp_longitude = _providers.get(0).getLongitude();
			GeoPoint providerLocation = new GeoPoint(
					(int) (temp_latitude * 1000000),
					(int) (temp_longitude * 1000000));
			
			drawPath(m_current_location, providerLocation, Color.RED);
			// Add the final pin
			Drawable drawable = this.getResources().getDrawable(
					R.drawable.current_location_marker_bw);
			MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(
					drawable, this);
			
			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);
		}
		

		m_loading_dialog.hide();
	}

	// method to draw the path.
	private void drawPath(GeoPoint current, GeoPoint destination, int color) {
		String mapURL = buildMapsURL(current, destination);
		String encoded = InternetHelper.httpGetRequest(mapURL);
		
		decodePoints(encoded);

		for (int i = 1; i < m_pathList.size(); i++) {
			mapOverlays.add(new RouteOverlay(m_pathList.get(i - 1), m_pathList
					.get(i), color));
		}
	}

	// decode the points
	private void decodePoints(String encoded) {
		// get only the encoded geopoints
		
		encoded = encoded.split("points:\"")[1].split("\",")[0];
		// replace two backslashes by one (some error from the transmission)
		encoded = encoded.replace("\\\\", "\\");

		// decoding
		List<GeoPoint> poly = new ArrayList<GeoPoint>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		//algorithm for decoding the points from google.
		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
			GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),
					(int) (((double) lng / 1E5) * 1E6));
			poly.add(p);
		}

		m_pathList = poly;
	}

	// method to build the maps url
	private String buildMapsURL(GeoPoint src, GeoPoint dest) {

		StringBuilder urlString = new StringBuilder();

		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr=");
		urlString.append(Double.toString((double) src.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString
				.append(Double.toString((double) src.getLongitudeE6() / 1.0E6));
		urlString.append("&daddr=");// to
		urlString
				.append(Double.toString((double) dest.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString
				.append(Double.toString((double) dest.getLongitudeE6() / 1.0E6));
		urlString.append("&ie=UTF8&0&om=0&output=dragdir");

		return urlString.toString();
	}

	/**
	 * Grabs the providers from the previous activity, if any
	 */
	private void displayAllProvidersOnMap() {
		Provider intentProvider = (Provider) getIntent().getSerializableExtra(
				"providers");
		//temporarily generating providers until database is set up.
		if (intentProvider == null)
			generateProviderList();
		else
			_providers.add(intentProvider);

		//temporarily center the map to Penn
		GeoPoint pennLocation = new GeoPoint(this.pennLatitude, this.pennLongitude);
		_myMapController.animateTo(pennLocation);

		// setup additional "pins" to the map
		mapOverlays = _myMapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.current_location_marker_bw);
		MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable,
				this);
		itemizedoverlay.setProviders(_providers);

		//add each pin for each provider.
		for (int i = 0; i < _providers.size(); i++) {
			GeoPoint p = new GeoPoint(
					(int) (_providers.get(i).getLatitude() * 1000000),
					(int) (_providers.get(i).getLongitude() * 1000000));
			OverlayItem overlayitem = new OverlayItem(p, "", "");
			itemizedoverlay.addOverlay(overlayitem);
		}

		// adding yourself comes after the location has been received.
		mapOverlays.add(itemizedoverlay);

		//refresh map
		_myMapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void generateProviderList() {
		try{
			String uri = "http://spectrackulo.us/350/";
			String history_JSON = InternetHelper.httpGetRequest(uri);
			_providers = ProviderHelper.createListOfProvidersFromJson(history_JSON);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}

package edu.upenn.cis350;
import android.app.Activity;
import com.google.android.maps.MapActivity;
import android.os.Bundle;


public class MapProviderActivity extends MapActivity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        
    }

	@Override
	public void onResume(){
		super.onResume();
        
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
}

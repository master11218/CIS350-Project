package edu.upenn.cis350;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import edu.upenn.cis350.entities.Provider;
import android.content.Context;
import android.app.Dialog;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An overlay on the map that allows user to interact with the pin on the map.
 * The provider info digest is displayed when the corresponding point on the map
 * was tapped
 * 
 */
public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private Context mContext;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private ArrayList<Provider> _providers = new ArrayList<Provider>();
	
	public MapItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	public void setProviders(ArrayList<Provider> providers){
		this._providers = providers;
	}
	
	@Override
	protected boolean onTap(int index) {
		Dialog dialog = new Dialog(mContext);
		Provider currentProvider;
		try{
			currentProvider = _providers.get(index);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}

		dialog.setContentView(R.layout.map_custom_dialog);
		dialog.setTitle(currentProvider.getName());

		TextView addressText = (TextView) dialog.findViewById(R.id.map_provider_address);
		addressText.setText(currentProvider.getAddress());
		
		TextView phoneText = (TextView) dialog.findViewById(R.id.map_provider_phone);
		phoneText.setText(currentProvider.getPhone());
		
		TextView ratingText = (TextView) dialog.findViewById(R.id.map_provider_rating);
		Double rating = currentProvider.getAverageRating();
		//if it's not 0.0, that means it's an actual rating, so display it
		String rating_text = "";
		if(rating != -1)
			rating_text = "Average rating: " + rating.toString();
		if(rating != 0.0) ratingText.setText(rating_text);
		//else if it is, then it's the actual user's location
		else ratingText.setText("");
		
		dialog.show();
		return true;
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}

	@Override
	public int size() {
	  return mOverlays.size();
	}
}

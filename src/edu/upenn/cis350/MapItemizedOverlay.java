package edu.upenn.cis350;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import android.content.Context;
import android.app.Dialog;
import android.widget.TextView;

import java.util.ArrayList;

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

		Provider currentProvider = _providers.get(index);

		dialog.setContentView(R.layout.map_custom_dialog);
		dialog.setTitle(currentProvider.getName());

		TextView addressText = (TextView) dialog.findViewById(R.id.map_provider_address);
		addressText.setText(currentProvider.getAddress());
		
		TextView phoneText = (TextView) dialog.findViewById(R.id.map_provider_phone);
		phoneText.setText(currentProvider.getPhone());
		
		TextView ratingText = (TextView) dialog.findViewById(R.id.map_provider_rating);
		Double rating = currentProvider.getAvgRating();
		ratingText.setText(rating.toString());
		
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

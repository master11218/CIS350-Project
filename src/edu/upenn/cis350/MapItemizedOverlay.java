package edu.upenn.cis350;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private Context mContext;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public MapItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			// this is the method to call when the button is clicked 
			public void onClick(DialogInterface dialog, int id) {
				/*
				 * Can someone please fill in the code to 
				 * redirect to the profile page?
				 */
			}
		});
	  
	  dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			// this is the method to call when the button is clicked 
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
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

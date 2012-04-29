package edu.upenn.cis350;

import java.util.ArrayList;

import edu.upenn.cis350.util.MyLocation;
import edu.upenn.cis350.util.MyLocation.LocationResult;
import edu.upenn.cis350.entities.Provider;
import edu.upenn.cis350.util.ProviderHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity displays all the result from the search page. It first gets the
 * user search criteria from the searchActivity and then load the matched
 * information by making a request to the backend server
 * 
 * @author henryou
 * 
 */
public class SearchResultActivity extends Activity{

	private MyLocation myLocation = new MyLocation();
	private Double m_latitude;
	private Double m_longitude;
	private ProgressDialog m_loading_dialog;
	private ListView providerList;
	ArrayList<Provider> satisfiedproviders = new ArrayList<Provider>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        
        Bundle extras = getIntent().getExtras(); 
        
        if (extras != null) {
        	//Fetch all the parameters from the caller activity
			String provider_name = extras.getString("provider_name");
			String has_parking = extras.getString("has_parking");
			String accepting_new = extras.getString("accepting_new");
			String handicap = extras.getString("handicap");
			String appointment_only = extras.getString("appointment_only");
			String credit_card = extras.getString("credit_card");
			String type = extras.getString("type");
			String distance = extras.getString("distance");
			
			//Only load the current location when the search criteria includes distance
			//This saves the time it takes to load the search result in most cases
			if (distance.length()>0){
				locationClick();
				//load the longitude and latitude here
				this.m_loading_dialog = ProgressDialog.show(this, "", 
	    	            "Finding your current location. Please wait...", true);
			}
			
			//Obtain a list of satisfied provider by querying the backend database
			this.satisfiedproviders = ProviderHelper.getSatisfiedProvider(provider_name, has_parking, 
	        		accepting_new, handicap,appointment_only,credit_card,type,distance, this.m_latitude, this.m_longitude); 
        
			if (this.satisfiedproviders.size()==0){
				Context context = getApplicationContext();
				Toast toast = Toast.makeText(context, "No provider found. Please refine your search criteria",Toast.LENGTH_SHORT);
				toast.show();
				finish();
			}
			
			//Set up the list view for the search results
			this.providerList = (ListView)this.findViewById(R.id.search_result_list);
			this.providerList.setAdapter(new SearchResultAdapter(this));
        
        }
        
        
    }

    private void locationClick() {
	    myLocation.getLocation(this, locationResult);
	}
	
	public LocationResult locationResult = new LocationResult(){
	    @Override
	    public void gotLocation(final Location location){
	        	m_latitude = location.getLatitude();
	        	m_longitude = location.getLongitude();
	    		m_loading_dialog.hide();
	        }
	    	
	    };
	
	/**
	 * An adapter used to display every search result loaded
	 * @author henryou
	 *
	 */
    class SearchResultAdapter extends BaseAdapter{
		private Context m_context;
		public SearchResultAdapter(Context c){
			m_context = c;
		}
		public int getCount() {
			if(satisfiedproviders != null)
				return satisfiedproviders.size();
			else
				return 0;
		}
		public Object getItem(int position) {
			if(satisfiedproviders != null)
				return satisfiedproviders.get(position);
			else
				return 0;
		}
		public long getItemId(int position) {
			return position;
		}
		
		/**
		 * Every view should include a brief summary of the provider information and
		 * a button link to the detailed information page 
		 */
		public View getView(final int position, View convertView, ViewGroup parent) {
			//inflate the view
			LinearLayout list_result;
			if(convertView == null){
				LayoutInflater inf = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				list_result = (LinearLayout) inf.inflate(R.layout.search_result_entry, null);
			}
			else {
				list_result = (LinearLayout)convertView;
			}
				
			//populate the new view
			TextView providerName = (TextView)list_result.findViewById(R.id.search_result_name);
			final Provider currProvider = satisfiedproviders.get(position);
			String tempName = currProvider.getName();
			providerName.setText(tempName);

			
			Button viewButton = (Button)list_result.findViewById(R.id.search_result_button);
			viewButton.setOnClickListener(new OnClickListener(){
				public void onClick(View arg0) {
					Intent i = new Intent(SearchResultActivity.this, ProviderProfileActivity.class);
					i.putExtra("providers", currProvider);
					
					startActivity(i);
				}
			});	

			return list_result;
		}
	}

}

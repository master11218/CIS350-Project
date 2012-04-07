package edu.upenn.cis350;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HistoryActivity extends Activity{
	private ArrayList<Rating> _ratings;
	private ListView m_results;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
        m_results = (ListView)this.findViewById(R.id.history_res);
        m_results.setAdapter(new HistoryAdapter(this));

		long id = (Long)getIntent().getSerializableExtra("id");

		String uri = "http://spectrackulo.us/350/history.php?uid=" + id;
		System.out.println(uri);

		HttpRequest http = new HttpRequest();
		String history = http.execHttpRequest(uri, HttpRequest.HttpMethod.Get, "");
		System.out.println(history);

		_ratings = new ArrayList<Rating>();
		try{
			JSONObject json = new JSONObject(history);
			JSONArray reviews = json.getJSONArray("reviews");
			
			for(int i=0;i < reviews.length();i++){						
				JSONObject current = reviews.getJSONObject(i);
				Rating currentRating = new Rating(
						id, Long.parseLong(current.getString("pid")), 
						current.getString("time"), current.getString("review"),
						Integer.parseInt(current.getString("rating")));
				_ratings.add(currentRating);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	class HistoryAdapter extends BaseAdapter{
		private Context m_context;
		public HistoryAdapter(Context c){
			m_context = c;
		}
		public int getCount() {
			if(_ratings != null)
				return _ratings.size();
			else
				return 0;
		}
		public Object getItem(int position) {
			if(_ratings != null)
				return _ratings.get(position);
			else
				return 0;
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(final int position, View convertView, ViewGroup parent) {
	        RelativeLayout list_result;
	        if(convertView == null){
	        	LayoutInflater inf = (LayoutInflater)m_context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				list_result = (RelativeLayout) inf.inflate(R.layout.history_result, null);
	        }
	        else
	        	list_result = (RelativeLayout)convertView;
	        
	        //populate the inflated view.
	        TextView providerName = (TextView)list_result.findViewById(R.id.history_activity_provider_name);
	        String uri = "http://spectrackulo.us/350/provider.php?pid=" + _ratings.get(position).getProvider();
	        System.out.println(uri);
			HttpRequest http = new HttpRequest();
			final String pid = http.execHttpRequest(uri, HttpRequest.HttpMethod.Get, "");
			providerName.setText(pid);
	        
	        TextView rating = (TextView)list_result.findViewById(R.id.history_activity_rating);
	        rating.setText("Rating: " + _ratings.get(position).getRating().toString());
	        
	        TextView review = (TextView)list_result.findViewById(R.id.history_activity_review);
	        review.setText(_ratings.get(position).getReview());

	        TextView date = (TextView)list_result.findViewById(R.id.history_activity_date_visited);
	        date.setText(_ratings.get(position).getDate());

	        Button b = (Button)list_result.findViewById(R.id.history_activity_button);
	        b.setOnClickListener(new OnClickListener(){
				
				public void onClick(View arg0) {
					Intent intent = new Intent(m_context, ProviderProfileActivity.class);
					String uri = "http://spectrackulo.us/350/?pid=" + _ratings.get(position).getProvider();

					HttpRequest http = new HttpRequest();
					String provider = http.execHttpRequest(uri, HttpRequest.HttpMethod.Get, "");

					Provider buttonProvider;
					try{
						JSONObject fakeJson = new JSONObject(provider);
						String actualJsonString = fakeJson.getString("provider");
						JSONObject json = new JSONObject(actualJsonString);
						System.out.println(json.getString("name"));
						
						buttonProvider = new Provider(Long.parseLong(json.getString("pid")), json.getString("name"), json.getString("address"), 
								json.getString("city"), json.getString("state"), json.getString("zip"), json.getString("phone"),
								json.getString("accepting_new"), json.getString("has_parking"),
								json.getString("type"), json.getString("credit_cards"), json.getString("handicap_access"),
								json.getString("appointment_only"), Double.parseDouble(json.getString("average_rating")),
								Double.parseDouble(json.getString("longitude")), Double.parseDouble(json.getString("latitude")), json.getString("website"), json.getString("hours"));
						
						intent.putExtra("providers", buttonProvider);
						System.out.println(buttonProvider);
						startActivity(intent);
					} catch (Exception e){
						e.printStackTrace();
					}
				}
	        });
			return list_result;
		}
	}

	@Override
	public void onResume(){
		super.onResume();
		if (_ratings.size()<1) {
			TextView nohistory= (TextView)this.findViewById(R.id.history_no_history_message);
			nohistory.setText("You have not rated any providers yet");
		}
	}
}

package edu.upenn.cis350;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.upenn.cis350.entities.Provider;
import edu.upenn.cis350.entities.Rating;
import edu.upenn.cis350.util.InternetHelper;
import edu.upenn.cis350.util.ProviderHelper;

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
	private static final String BASE_P_URL = "http://spectrackulo.us/350/history.php?uid=";
	private ArrayList<Rating> _ratings;
	private ListView m_results;

	@Override
	/**
	 * Set up the view each time the History Activity is brought up
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		//load the List to be used in the ListView in m_results
		m_results = (ListView)this.findViewById(R.id.history_res);
		//loads the Adapter required for adapting the data to the ListView
		m_results.setAdapter(new HistoryAdapter(this));
		//extracts the id from the Intent in Long format
		long user_id = (Long)getIntent().getSerializableExtra("id");

		//The uri is the connection to our back-end
		String uri = BASE_P_URL + user_id;

		//history_JSON is a String that contains encoded information for the provider
		String history_JSON = InternetHelper.httpGetRequest(uri);

		//ratings is an ArrayList which contains all of Ratings instances pertaining to a provider
		_ratings = populateRatings(user_id, history_JSON);	
	}

	/**
	 * Helper method that parses a JSON object to return an ArrayList of Ratings
	 * @param user_id The user id of the person who we want ratings for.
	 * @param history_JSON The string representation of the JSON object we want to parse.
	 * @return The ArrayList of the Ratings from the JSON object
	 */
	private ArrayList<Rating> populateRatings(long user_id, String history_JSON){
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		try{
			//First, obtain the user's history in JSON format and put it into a JSONArray
			JSONObject jsonHistory = new JSONObject(history_JSON);
			JSONArray reviews = jsonHistory.getJSONArray("reviews");

			//Add to the _ratings array a new Rating instance for each provider
			for(int i=0;i < reviews.length();i++){						
				//current contains the information on the provider being analyzed, in JSON format
				JSONObject current = reviews.getJSONObject(i);
				long provider_id = Long.parseLong(current.getString("pid"));
				String time = current.getString("time");
				String review = current.getString("review");
				int rating = Integer.parseInt(current.getString("rating"));
				//Create a Ratings instance for each provider and add to the _ratings array
				Rating currentRating = new Rating(user_id, provider_id, time, review, rating);
				ratings.add(currentRating);
			}
		} catch (Exception e){
			//for logging
			e.printStackTrace();
		}	
		return ratings;
	}

	@Override
	public void onResume(){
		super.onResume();
		if (_ratings.size()<1) {
			TextView nohistory= (TextView)this.findViewById(R.id.history_no_history_message);
			nohistory.setText("You have not rated any providers yet");
		}
	}


	class HistoryAdapter extends BaseAdapter{
		private Context m_context;

		public HistoryAdapter(Context context){
			m_context = context;
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
			} else {
				list_result = (RelativeLayout)convertView;
			}

			//get the current rating object
			Rating currentRating = _ratings.get(position);

			//populate the inflated view.
			TextView providerName = (TextView)list_result.findViewById(R.id.history_activity_provider_name);
			String uri = "http://spectrackulo.us/350/provider.php?pid=" + currentRating.getProvider();
			
			final String pid = InternetHelper.httpGetRequest(uri);
			providerName.setText(pid);

			//get the rating
			Integer rating_int = currentRating.getRating();
			TextView rating_textBox = (TextView)list_result.findViewById(R.id.history_activity_rating);
			rating_textBox.setText("Rating: " + rating_int.toString());

			TextView review = (TextView)list_result.findViewById(R.id.history_activity_review);
			review.setText(currentRating.getReview());

			TextView date = (TextView)list_result.findViewById(R.id.history_activity_date_visited);
			date.setText(currentRating.getDate());

			Button button = (Button)list_result.findViewById(R.id.history_activity_button);
			button.setOnClickListener(new OnClickListener(){
				public void onClick(View arg0) {
					Intent intent = new Intent(m_context, ProviderProfileActivity.class);
					String uri = "http://spectrackulo.us/350/?pid=" + _ratings.get(position).getProvider();

					String provider = InternetHelper.httpGetRequest(uri);
							
					Provider buttonProvider;
					try{
						JSONObject fakeJson = new JSONObject(provider);
						String actualJsonString = fakeJson.getString("provider");
						JSONObject json = new JSONObject(actualJsonString);

						buttonProvider = ProviderHelper.createProviderFromJson(json);

						intent.putExtra("providers", buttonProvider);
						startActivity(intent);
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			});
			return list_result;
		}
	}
}
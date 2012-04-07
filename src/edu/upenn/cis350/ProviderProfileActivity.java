package edu.upenn.cis350;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.upenn.cis350.entities.Provider;
import edu.upenn.cis350.entities.Rating;
import edu.upenn.cis350.util.HttpRequest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ProviderProfileActivity extends Activity{

	private static final String BASE_URL="http://spectrackulo.us/350/ratings.php?mode=view&pid=";
	private TextView m_provider_name;
	private TextView m_provider_phone;
	private TextView m_provider_address;
	private TextView m_provider_rating;


	private Button m_button_map;
	private Button m_button_review;
	private final Context m_context = this;
	private ArrayList<Rating> m_ratings;
	private Provider m_provider;
	private ListView m_comments;
	private ImageView m_provider_star_rating;
	private Button parking;
	private Button creditcard;
	private Button accepting;
	private Button appointment; 
	private Button PCP; 
	private Button specialist;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.provider_pf);
		m_button_map = (Button)this.findViewById(R.id.button_providerpf_map);
		m_button_review = (Button)this.findViewById(R.id.providerpf_rate_button);
		m_comments = (ListView)this.findViewById(R.id.providerpf_comments);
		m_comments.setAdapter(new RatingAdapter(m_context));

		m_provider_name = (TextView)this.findViewById(R.id.provider_name);
		m_provider_phone = (TextView)this.findViewById(R.id.provider_phone);
		m_provider_address = (TextView)this.findViewById(R.id.provider_address);
		m_provider_rating = (TextView)this.findViewById(R.id.providerpf_average_rating_text);
		m_provider_star_rating = (ImageView) this
				.findViewById(R.id.providerpf_average_stars);
		
		//icons for attributes
		parking = (Button) this.findViewById(R.id.provider_parking);
		creditcard = (Button) this
				.findViewById(R.id.provider_creditcard);
		accepting = (Button) this.findViewById(R.id.provider_accepting);
		appointment = (Button) this
				.findViewById(R.id.provider_appointments);
		PCP = (Button) this.findViewById(R.id.provider_PCP);
		specialist = (Button) this
				.findViewById(R.id.provider_specialist);
	}


	@Override
	public void onResume() {
		super.onResume();
		// get existing Intent data (the ID of provider to be looked at), and
		// initialize a list of ratings to be populated for the provider.
		m_provider = (Provider) getIntent().getSerializableExtra("providers");
		m_ratings = new ArrayList<Rating>();

		//populate ratings, for RatingAdapter
		populateRatings();

		// initialize buttons, map, review, passing on the now-initialized
		// provider
		m_button_map.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (v == m_button_map) {

				}
				Intent intent = new Intent(m_context, MapProviderActivity.class);
				intent.putExtra("providers", m_provider);
				startActivity(intent);
				m_button_map.setBackgroundResource(R.drawable.map2);
			}
		});

		//review dialog pops up.
		m_button_review.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Dialog dialog = new Dialog(m_context);

				dialog.setContentView(R.layout.provider_pf_rate);
				dialog.setTitle("Rate and Review this Provider!");
				dialog.show();
			}
		});

		// set the provider info in TextViews
		m_provider_name.setText(m_provider.getName());
		m_provider_phone.setText(m_provider.getPhone());
		m_provider_address.setText(m_provider.getAddress());
		Double averageRating = m_provider.getAverageRating();
		m_provider_rating.setText(averageRating.toString());
		m_provider_address.setText(m_provider.getAddress() + ", "
				+ m_provider.getCity() + ", " + m_provider.getState() + "  "
				+ m_provider.getZip());


		// show or hide icons as appropriate
		toggleIconVisibility(parking,m_provider.getParking());
		toggleIconVisibility(creditcard,m_provider.getCreditCards());
		toggleIconVisibility(appointment,m_provider.getAppointment());
		toggleIconVisibility(accepting,m_provider.getAccepting());
		toggleIconVisibility(PCP,m_provider.getType());
		
		//
		setRatingImage();

	}

	private void populateRatings() {
		// make the HttpRequest
		String uri = BASE_URL + m_provider.getID();
		HttpRequest requestManager = new HttpRequest();
		String ratingsJSON = requestManager.execHttpRequest(uri,
				HttpRequest.HttpMethod.Get, "");
		// parse the JSON and populate m_ratings from JSON for m_provider
		try {
			JSONObject json = new JSONObject(ratingsJSON);
			JSONArray reviews = json.getJSONArray("reviews");
			for (int i = 0; i < reviews.length(); i++) {
				JSONObject current = reviews.getJSONObject(i);
				Rating currentRating = new Rating(Long.parseLong(current
						.getString("uid")), Long.parseLong(current
						.getString("pid")), current.getString("time"),
						current.getString("review"), Integer.parseInt(current
								.getString("rating")));
				m_ratings.add(currentRating);
			}
		} catch (Exception e) {
			// for logging
			e.printStackTrace();
		}
	}
	
	private void setRatingImage(){
		Integer avg = (int) m_provider.getAverageRating();

		m_provider_star_rating.setImageResource(R.drawable.onestar);
		if (avg == 5) {
			m_provider_star_rating.setImageResource(R.drawable.fivestars);
		} else if (avg == 4) {
			m_provider_star_rating.setImageResource(R.drawable.fourstars);
		} else if (avg == 3) {
			m_provider_star_rating.setImageResource(R.drawable.threestars);
		} else if (avg == 2) {
			m_provider_star_rating.setImageResource(R.drawable.twostars);
		} else if (avg == 4) {
			m_provider_star_rating.setImageResource(R.drawable.onestar);
		}
	}

	private void toggleIconVisibility(Button button, String result){
		if (result.equals("yes") || result.equals("PCP")) {
			button.setVisibility(Button.VISIBLE);
		} else {
			button.setVisibility(Button.GONE);
		}
	}
	
	class RatingAdapter extends BaseAdapter{
		private Context m_context;
		public RatingAdapter(Context c){
			m_context = c;
		}
		public int getCount() {
			if(m_ratings != null)
				return m_ratings.size();
			else
				return 0;
		}
		public Object getItem(int position) {
			if(m_ratings != null)
				return m_ratings.get(position);
			else
				return 0;
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(final int position, View convertView, ViewGroup parent) {


			//inflate the view
			LinearLayout list_result;
			if(convertView == null){
				LayoutInflater inf = (LayoutInflater)m_context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				list_result = (LinearLayout) inf.inflate(R.layout.provider_pf_comment, null);

			}
			else
				list_result = (LinearLayout)convertView;
			//populate the new view
			//TextView tv_rating = (TextView)list_result.findViewById(R.id.providerpf_comment_rating);
			Integer temp = m_ratings.get(position).getRating();
			
			if (temp==5) {
				ImageView stars= (ImageView)list_result.findViewById(R.id.providerpf_comment_stars);
				stars.setImageResource(R.drawable.fivestars);
			} else if (temp==4) {
				ImageView stars= (ImageView)list_result.findViewById(R.id.providerpf_comment_stars);
				stars.setImageResource(R.drawable.fourstars);
			} else if (temp==3) {
				ImageView stars= (ImageView)list_result.findViewById(R.id.providerpf_comment_stars);
				stars.setImageResource(R.drawable.threestars);
			} else if (temp==2) {
				ImageView stars= (ImageView)list_result.findViewById(R.id.providerpf_comment_stars);
				stars.setImageResource(R.drawable.twostars);
			} else if (temp==1) {
				ImageView stars= (ImageView)list_result.findViewById(R.id.providerpf_comment_stars);
				stars.setImageResource(R.drawable.onestar);
			}

			TextView tv_provider_desc = (TextView)list_result.findViewById(R.id.providerpf_comment_review);
			tv_provider_desc.setText(m_ratings.get(position).getReview());

			TextView tv_provider_date = (TextView)list_result.findViewById(R.id.providerpf_comment_date);
			tv_provider_date.setText(m_ratings.get(position).getDate().toString());

			return list_result;
		}
	}
	public static String convertText(boolean input) {
		if (input) {
			return "yes";
		} else {
			return "no";
		}
	}
}

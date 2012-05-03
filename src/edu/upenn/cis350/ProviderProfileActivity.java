package edu.upenn.cis350;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.upenn.cis350.entities.Provider;
import edu.upenn.cis350.entities.Rating;
import edu.upenn.cis350.util.InternetHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Contains information about a provider
 * @author DXU
 *
 */
public class ProviderProfileActivity extends Activity{

	private static final String BASE_URL="http://spectrackulo.us/350/ratings.php?mode=view&pid=";
	private TextView m_provider_name;
	private TextView m_provider_phone;
	private TextView m_provider_address;
	private TextView m_provider_rating;
	
	private Dialog dialog;

	private EditText reviewText;
	private Button reviewButton;
	private RatingBar ratingbar;
	private RatingBar rating_friendliness_bar;
	private RatingBar rating_communication_bar;
	private RatingBar rating_environment_bar;
	

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
	
	private RatingAdapter m_adapter;

	/**
	 * Create each provider's profile
	 */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.provider_pf);
		//layout elements
		m_button_map = (Button)this.findViewById(R.id.button_providerpf_map);
		m_button_review = (Button)this.findViewById(R.id.providerpf_rate_button);
		m_comments = (ListView)this.findViewById(R.id.providerpf_comments);
		m_adapter = new RatingAdapter(m_context);
		m_comments.setAdapter(m_adapter);

		//provider metadata
		m_provider_name = (TextView)this.findViewById(R.id.provider_name);
		m_provider_phone = (TextView)this.findViewById(R.id.provider_phone);
		m_provider_address = (TextView)this.findViewById(R.id.provider_address);
		m_provider_rating = (TextView)this.findViewById(R.id.providerpf_average_rating_text);
		m_provider_star_rating = (ImageView) this
				.findViewById(R.id.providerpf_average_stars);

		//Initialize the icons of "has parking", "appointment only", etc
		this.initializeIcons();	
	}

	/**
	 * Set up icons for attributes. Add a listener to each button so that a
	 * toast is shown to explain what the icon means once being clicked
	 */
	private void initializeIcons() {
		
		parking = (Button) this.findViewById(R.id.provider_parking);
		this.addDescriptionToast(parking, "The provider has parking");
		creditcard = (Button) this.findViewById(R.id.provider_creditcard);
		this.addDescriptionToast(creditcard, "The provider accepts credit card");
		accepting = (Button) this.findViewById(R.id.provider_accepting);
		this.addDescriptionToast(accepting, "This provider accepts new patient");
		appointment = (Button) this.findViewById(R.id.provider_appointments);
		this.addDescriptionToast(appointment, "Appointment Only");
		PCP = (Button) this.findViewById(R.id.provider_PCP);
		this.addDescriptionToast(PCP, "Primary Care Provider");
		specialist = (Button) this.findViewById(R.id.provider_specialist);
		this.addDescriptionToast(specialist, "Specialist");

	}

	/**
	 * add a listen to the icon button so that a description is displayed when
	 * clicked
	 * 
	 * @param icon
	 * @param description
	 */
	private void addDescriptionToast(Button icon, final String description) {
		icon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Context context = getApplicationContext();
				Toast toast = Toast.makeText(context, description,Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
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

		// initialize buttons, map, review, passing on the now-initialized provider
		m_button_map.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (v == m_button_map) {

				}
				Intent intent = new Intent(m_context, MapProviderActivity.class);
				intent.putExtra("providers", m_provider);
				startActivity(intent);
			}
		});

		//review dialog pops up.
		m_button_review.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog = new Dialog(m_context);

				dialog.setContentView(R.layout.provider_pf_rate);
				dialog.setTitle("Rate and Review this Provider!");
				
				
				
				reviewText = (EditText) dialog.findViewById(R.id.providerpf_rate_review);
				reviewButton = (Button) dialog.findViewById(R.id.providerpf_rate_button_submit);
				ratingbar = (RatingBar) dialog.findViewById(R.id.providerpf_rate_bar);
				rating_communication_bar = (RatingBar) dialog.findViewById(R.id.providerpf_rate_communication_bar);
				rating_environment_bar = (RatingBar) dialog.findViewById(R.id.providerpf_rate_environment_bar);
				rating_friendliness_bar = (RatingBar) dialog.findViewById(R.id.providerpf_rate_friendly_bar);
				
				reviewButton.setOnClickListener(new OnClickListener(){

					public void onClick(View arg0) {
						

						String review = reviewText.getText().toString();
						
						//make sure the input for keyword search is correct
						if (review.length()>0 && !review.matches("[A-Za-z0-9\\s\\.,'!?&&[^\\n]]+?")){
							//tell user the input was invalid
							Context context = getApplicationContext();
							Toast toast = Toast.makeText(context, "The keyword for search should only contains" +
									" English characters, numbers or white space",Toast.LENGTH_SHORT);
							toast.show();
							return;
						}else{
							review = review.replace(" ", "%20");
						}
						
						
						SharedPreferences settings = getSharedPreferences("UserData", 0);
						System.out.println(settings);
						String id = settings.getString("Id", null);
						float rating = ratingbar.getRating();
						float friendliness = rating_friendliness_bar.getRating();
						float communication = rating_communication_bar.getRating();
						float environment = rating_environment_bar.getRating();
						m_provider.getID();
						String temp_base = "http://www.spectrackulo.us/350/ratings.php?mode=insert";
						String url = temp_base + "&pid=" + m_provider.getID() + "&uid=" + id + "&rating=" + 
								(int)rating + "&review=" + review + "&friendliness=" + (int)friendliness + 
								"&communication=" + (int)communication + "&office_environment=" + (int)environment;
						System.out.println(url);
						InternetHelper.httpGetRequest(url);
						Toast.makeText(m_context, "Review submitted!", Toast.LENGTH_LONG).show();
						populateRatings();
						dialog.hide();	
					}
				});
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
		
		setRatingImage();
	}

	
	private void populateRatings() {
		// make the HttpRequest
		String uri = BASE_URL + m_provider.getID();		
		String ratingsJSON = InternetHelper.httpGetRequest(uri);
		
		// parse the JSON and populate m_ratings from JSON for m_provider
		try {
			JSONObject json = new JSONObject(ratingsJSON);
			JSONArray reviews = json.getJSONArray("reviews");
			for (int i = 0; i < reviews.length(); i++) {
				JSONObject current = reviews.getJSONObject(i);
				long user_id = Long.parseLong(current.getString("uid"));
				long provider_id = Long.parseLong(current.getString("pid"));
				String time = current.getString("time");
				String review = current.getString("review");
				float rating = Float.parseFloat(current.getString("rating"));
				float friendliness = Float.parseFloat(current.getString("friendliness"));
				float communication = Float.parseFloat(current.getString("communication"));
				float environment = Float.parseFloat(current.getString("office_environment"));
				
				Rating currentRating = new Rating(user_id, provider_id, time, review, (int)rating, (int)communication, (int)environment, (int)friendliness);
				m_ratings.add(currentRating);
				m_adapter.notifyDataSetChanged();
				
			}
		} catch (Exception e) {
			// for logging
			System.out.println("Ratings error");
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
		}
		else if(result.equals("PCP")){
			specialist.setVisibility(Button.GONE);
			PCP.setVisibility(Button.VISIBLE);
		}
		else if(result.equals("specialist")){
			specialist.setVisibility(Button.VISIBLE);
			PCP.setVisibility(Button.GONE);
		}
		else {
			button.setVisibility(Button.GONE);
		}
	}
	
	//inner class for rating adapter. Needs to reference m_ratings
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
			
			Rating currentRating = m_ratings.get(position);
			//populate the new view
			//TextView tv_rating = (TextView)list_result.findViewById(R.id.providerpf_comment_rating);
			Integer rating = currentRating.getRating();
			ImageView stars= (ImageView)list_result.findViewById(R.id.providerpf_comment_stars);
			RatingBar friendliness = (RatingBar)list_result.findViewById(R.id.providerpf_comment_friendliness);
			RatingBar environment = (RatingBar)list_result.findViewById(R.id.providerpf_comment_environment);
			RatingBar communication = (RatingBar)list_result.findViewById(R.id.providerpf_comment_communication);
			
			if (rating==5) {
				stars.setImageResource(R.drawable.fivestars);
			} else if (rating==4) {
				stars.setImageResource(R.drawable.fourstars);
			} else if (rating==3) {
				stars.setImageResource(R.drawable.threestars);
			} else if (rating==2) {
				stars.setImageResource(R.drawable.twostars);
			} else if (rating==1) {
				stars.setImageResource(R.drawable.onestar);
			}
			
			friendliness.setRating(currentRating.getFriendliness_rating());

			environment.setRating(currentRating.getOffice_environment_rating());
			communication.setRating(currentRating.getCommunication_rating());
			
			String review = currentRating.getReview();
			String date = currentRating.getDate();

			TextView tv_provider_desc = (TextView)list_result.findViewById(R.id.providerpf_comment_review);
			tv_provider_desc.setText(review);

			TextView tv_provider_date = (TextView)list_result.findViewById(R.id.providerpf_comment_date);
			tv_provider_date.setText(date);

			return list_result;
		}
	}
}

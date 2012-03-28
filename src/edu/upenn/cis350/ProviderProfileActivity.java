package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Date;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.provider_pf);
		m_button_map = (Button)this.findViewById(R.id.button_providerpf_map);
		m_button_review = (Button)this.findViewById(R.id.providerpf_rate_button);
		m_comments = (ListView)this.findViewById(R.id.providerpf_comments);
		m_comments.setAdapter(new CommentAdapter(m_context));


		m_provider_name = (TextView)this.findViewById(R.id.provider_name);
		m_provider_phone = (TextView)this.findViewById(R.id.provider_phone);
		m_provider_address = (TextView)this.findViewById(R.id.provider_address);
		m_provider_rating = (TextView)this.findViewById(R.id.provider_rating);
	}


	@Override
	public void onResume(){
		super.onResume();
		//grab what's passed to it
		m_provider = (Provider)getIntent().getSerializableExtra("providers");
		//m_ratings = m_provider.getAverageRating();

		m_button_map.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(v==m_button_map) {
					m_button_map.setBackgroundResource(R.drawable.mapdown2);
				}
				Intent intent = new Intent(m_context, MapProviderActivity.class);
				intent.putExtra("providers", m_provider);
				startActivity(intent);
				m_button_map.setBackgroundResource(R.drawable.map2);
			}
		});
     

		m_button_review.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Dialog dialog = new Dialog(m_context);

				dialog.setContentView(R.layout.provider_pf_rate);
				dialog.setTitle("Rate and Review this Provider!");
				dialog.show();

			}
		});



		//set the provider info
		m_provider_name.setText(m_provider.getName());
		m_provider_phone.setText(m_provider.getPhone());
		m_provider_address.setText(m_provider.getAddress());
		Double averageRating = m_provider.getAverageRating();
		m_provider_rating.setText(averageRating.toString());

	}




	class CommentAdapter extends BaseAdapter{
		private Context m_context;
		public CommentAdapter(Context c){
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
			TextView tv_rating = (TextView)list_result.findViewById(R.id.providerpf_comment_rating);
			Integer temp = m_ratings.get(position).getRating();
			tv_rating.setText("Rating: " + temp.toString() + ".0");
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
}

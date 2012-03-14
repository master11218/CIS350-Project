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
		
		//Initialize a bunch of dummy ratings
		Rating first = new Rating(3,1,new Date(System.currentTimeMillis()), "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
				5);
		Rating second = new Rating(4,1,new Date(System.currentTimeMillis()), " adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehend",
				3);
		Rating third = new Rating(5,1,new Date(System.currentTimeMillis()), " This guy is awesome!!!!!!!!!!!!!!!!!!!!!!!",
				3);
		Rating fourth= new Rating(5,1,new Date(System.currentTimeMillis()), " I don't speak latin );",
				4);
		m_ratings = new ArrayList<Rating>();
		
		m_ratings.add(first);
		m_ratings.add(second);
		m_ratings.add(third);
		m_ratings.add(fourth);
		
		//initialize a dummy provider.
		m_provider = new Provider(1,"Nadeem Abbhasi Ahmed", "3400 Spruce Street, 8 Ravdin, Philadelphia, PA", "(215)662-3228", m_ratings, 39.951481, -75.200987);
		
	}


	@Override
	public void onResume(){
		super.onResume();
		m_button_map.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(m_context, MapProviderActivity.class);
				startActivity(intent);
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
		m_provider_name.setText(m_provider.getPhone());
		m_provider_phone.setText(m_provider.getPhone());
		m_provider_address.setText(m_provider.getAddress());
		m_provider_rating.setText(m_provider.getAvgRating());
		
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

	        LinearLayout list_result;
	        if(convertView == null){
	        	LayoutInflater inf = (LayoutInflater)m_context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				list_result = (LinearLayout) inf.inflate(R.layout.provider_pf_comment, null);
				
	        }
	        else
	        	list_result = (LinearLayout)convertView;
	        TextView tv_rating = (TextView)list_result.findViewById(R.id.providerpf_comment_rating);
	        Integer temp = m_ratings.get(position).getRating();
	        tv_rating.setText("Rating: " + temp.toString() + ".0");
	        
	        TextView tv_provider_desc = (TextView)list_result.findViewById(R.id.providerpf_comment_review);
	        tv_provider_desc.setText(m_ratings.get(position).getReview());
	        
	        TextView tv_provider_date = (TextView)list_result.findViewById(R.id.providerpf_comment_date);
	        tv_provider_date.setText(m_ratings.get(position).getDate().toString());
	        		
			return list_result;
		}
	}
}

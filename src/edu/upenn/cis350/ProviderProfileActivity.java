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

public class ProviderProfileActivity extends Activity{
	private Button m_button_map;
	private Button m_button_review;
	private final Context m_context = this;
	private ArrayList<Rating> m_ratings;
	private Provider m_provider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provider_pf);
		m_button_map = (Button)this.findViewById(R.id.button_providerpf_map);
		m_button_review = (Button)this.findViewById(R.id.providerpf_rate_button);
		
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

/*
	        <LinearLayout
	            android:layout_width="fill_parent"
	            android:layout_height="wrap_content" 
	            android:orientation="vertical">

	            <TextView
	                android:layout_width="fill_parent"
	                android:layout_height="wrap_content"
	                android:text="Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." />
			            
			    <TextView
			        android:layout_width="wrap_content"
			        android:layout_height="wrap_content"
					android:layout_gravity="right"
			        android:text="2012/3/04" />
	        </LinearLayout>
	        */
	        LinearLayout list_result;
	        if(convertView == null){
	        	LayoutInflater inf = (LayoutInflater)m_context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				list_result = (LinearLayout) inf.inflate(R.layout.provider_pf_comment, null);
				
	        }
			list_result
			
			/*
			LinearLayout list_result;
			if(convertView == null){
				//inflate the view
				LayoutInflater inf = (LayoutInflater)m_context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				list_result = (LinearLayout) inf.inflate(R.layout.friends_list_result, null);
			}
			else 
				list_result = (LinearLayout)convertView;

			//name of friend
			TextView t = (TextView)list_result.findViewById(R.id.friends_list_listresult);
			t.setText(m_request.execHttpRequest(BASE_URI + "getName.php?userId=" + m_far_friends.get(position),
					HttpMethod.Get, null).trim());
			//distance from friend
			TextView t2 = (TextView)list_result.findViewById(R.id.friends_list_resultsdistance);

			t2.setText(String.format("%.5g%n", m_far_distances.get(position)) + " mi. away");

			Button b = (Button)list_result.findViewById(R.id.friends_list_invitefriend);
			final int a = position;
			final String temp = m_far_friends.get(position);
			b.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// insert invite code here
					// insert invite code here

					m_events.clear();
					invite_list = new Dialog(m_context);
					invite_list.setContentView(R.layout.events_layout);

		      		//remove the button
		      		Button b = (Button)invite_list.findViewById(R.id.events_button);
					b.setVisibility(View.GONE);

					String f = m_request.execHttpRequest(BASE_URI + "getEvents.php?id=" + M_ID, HttpRequest.HttpMethod.Get, null);

		      		StringTokenizer temp = new StringTokenizer(f, ",");
		      		while(temp.hasMoreTokens()){
		      			String s = temp.nextToken().trim();
		      			if(!s.equals(""))
		      				m_events.add(s);
		      		}

			      	ListView m_list = (ListView)invite_list.findViewById(R.id.events_listview);
			      	EventAdapter m_listadapter = new EventAdapter(m_context);
			      	m_list.setFocusable(false);
			      	m_list.setAdapter(m_listadapter);
			      	m_list.setClickable(true);
			      	m_list.setOnItemClickListener(new OnItemClickListener(){
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							//event id = arg2, uid = position
							m_request.execHttpRequest(BASE_URI + "inviteUserToEvent.php?eid=" + arg2 + "&uid=" + position, HttpRequest.HttpMethod.Get, null);

						}

			      	});






			      	invite_list.show();

				}

			});


			return list_result;
		}

			 */
			return null;
		}
	}
}

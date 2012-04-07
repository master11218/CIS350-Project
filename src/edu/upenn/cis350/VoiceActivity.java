package edu.upenn.cis350;

import edu.upenn.cis350.entities.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
/**
 * Main screen activity which leads to the other activities
 * @author DXU
 *
 */
public class VoiceActivity extends Activity {
	/** Called when the activity is first created. */
	private Button m_button_search;
	private Button m_button_profile;
	private Button m_button_history;
	private Button m_button_map;
	private Button m_button_contact;
	private final Context m_context = this;
	private User currentUser;


	public Long id;
	public String name, address, email, phone, gender;


	/**
	 * Set up the view upon creation
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
	}

	/**
	 * Set up the activity when the main screen is brought up
	 */
	public void onResume(){
		super.onResume();
		//Retrieve the user information shared across the application
		SharedPreferences userData = getSharedPreferences("UserData", 0);
		if(!userData.contains("Id")){
			//no user is found
			currentUser = null;
		} else {
			//parse the user data
			id = Long.parseLong(userData.getString("Id", "-1"));
			name = userData.getString("Name", "Not found");
			address = userData.getString("Address", "Not found");
			email = userData.getString("Email", "Not found");
			phone = userData.getString("Phone", "Not found");
			gender = userData.getString("Gender", "N/A");
			currentUser = new User(id, name, email, address, gender, phone);
		}

		m_button_search = (Button)this.findViewById(R.id.home_btn_search);
		m_button_profile= (Button)this.findViewById(R.id.home_btn_profile);
		m_button_history = (Button)this.findViewById(R.id.home_btn_history);
		m_button_map = (Button)this.findViewById(R.id.home_btn_map);
		m_button_contact = (Button)this.findViewById(R.id.home_btn_contact);
		
		//set the listeners for each button
		setListeners();


	}

	public void setListeners() {
		m_button_search.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent intent = new Intent(m_context, SearchActivity.class);
				startActivity(intent);
			}
		});
		m_button_profile.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(m_context, ProfileActivity.class);
				intent.putExtra("user", currentUser);
				startActivity(intent);
			}
		});
		m_button_history.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(id == null){
					Toast.makeText(m_context,"You must register your app before being " +
							"able to view your history. You can register in the 'profile' section.", 
							Toast.LENGTH_SHORT).show();
				} else {	
					Intent intent = new Intent(m_context, HistoryActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
				}
			}
		});
		m_button_map.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(m_context, MapProviderActivity.class);
				startActivity(intent);
			}
		});
		m_button_contact.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {

				Intent intent = new Intent(m_context, ContactActivity.class);
				startActivity(intent);
			}
		});
	}


}
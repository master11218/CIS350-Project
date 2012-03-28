package edu.upenn.cis350;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;

public class VoiceActivity extends Activity {
    /** Called when the activity is first created. */
    private Button m_button_search;
    private Button m_button_profile;
    private Button m_button_history;
    private Button m_button_map;
    private Button m_button_contact;
    private final Context m_context = this;
    private User currentUser;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	public void onResume(){
		super.onResume();
		
		
		
        SharedPreferences userData = getSharedPreferences("UserData", 0);
        if(!userData.contains("Id")){
        	currentUser = null;
        } else {
        	Long id = Long.parseLong(userData.getString("Id", "-1"));
        	String name = userData.getString("Name", "Not found");
        	String address = userData.getString("Address", "Not found");
        	String email = userData.getString("Email", "Not found");
        	String phone = userData.getString("Phone", "Not found");
        	String gender = userData.getString("Gender", "N/A");
        	currentUser = new User(id, name, email, address, gender, phone);
        }
        
        
        System.out.println(currentUser);
        
        m_button_search = (Button)this.findViewById(R.id.button_search);
        m_button_profile= (Button)this.findViewById(R.id.button_profile);
        m_button_history = (Button)this.findViewById(R.id.button_history);
        m_button_map = (Button)this.findViewById(R.id.button_map);
        m_button_contact = (Button)this.findViewById(R.id.button_contact);
        
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
	        	Intent intent = new Intent(m_context, HistoryActivity.class);
				startActivity(intent);
				
			}
        });
        m_button_map.setOnClickListener(new OnClickListener(){
        	
			public void onClick(View v) {
	        	Intent intent = new Intent(m_context, MapProviderActivity.class);
//	        	Provider empty = null;
//	        	intent.putExtra("providers", empty);
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
package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class VoiceActivity extends Activity {
    /** Called when the activity is first created. */
    private Button m_button_search;
    private Button m_button_profile;
    private Button m_button_history;
    private Button m_button_map;
    private Button m_button_contact;
    private final Context m_context = this;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }

	@Override
	public void onResume(){
		super.onResume();
        
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
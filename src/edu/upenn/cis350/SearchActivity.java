package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchActivity extends Activity{
	
	private Button temp;
	private final Context m_context = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        
    }

	@Override
	public void onResume(){
		super.onResume();
        temp = (Button)this.findViewById(R.id.search_temp);
        temp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
		        Intent i = new Intent(m_context, ProviderProfileActivity.class);
		        startActivity(i);
			}
        });
	}
    
}

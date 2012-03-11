package edu.upenn.cis350;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProviderProfileActivity extends Activity{
	private Button m_button_map;
	private Button m_button_review;
	private final Context m_context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provider_pf);
		m_button_map = (Button)this.findViewById(R.id.button_providerpf_map);
		m_button_review = (Button)this.findViewById(R.id.providerpf_rate_button);
		
		//initialize a dummy provider.
		//Provider p = new Provider();
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

}

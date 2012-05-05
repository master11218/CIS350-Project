package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * This is the page that displays the all the contact information of the app.
 * The phone number was made to be dialable and the email button also has the
 * link to sending email.
 * 
 */
public class ContactActivity extends Activity{

	Button phone;
	Button email;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
	}

	@Override
	public void onResume(){
		super.onResume();
		phone= (Button)this.findViewById(R.id.contact_info_call);
		email= (Button)this.findViewById(R.id.contact_info_email_now);
		
		email.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				
				Intent it= new Intent(Intent.ACTION_SEND);
				String[] tos= {"evanswu@nursing.upenn.edu"};
				it.putExtra(Intent.EXTRA_EMAIL, tos);
				it.putExtra(Intent.EXTRA_SUBJECT, "Comment on Consumer Voice");
				it.setType("text/plain");
				startActivity(it);
			}
		});
		
		phone.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Log.v("button pressed", "button pressed");
				Intent intent= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:12152552555"));
				startActivity(intent);
			}
		});

	}

}

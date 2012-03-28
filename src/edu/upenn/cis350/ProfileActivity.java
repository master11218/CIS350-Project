package edu.upenn.cis350;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

public class ProfileActivity extends Activity{
	private User _userToLoad;
	private Button save;
	
	private String gender;
	private String phone;
	private String address;
	private String email;
	private String name;
	
	private EditText nameField;
	private EditText emailField;
	private EditText addressField;
	private EditText phoneField;
	private RadioGroup genderField;
	
	private Context ownContext = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        _userToLoad = (User)getIntent().getSerializableExtra("user");
        if(_userToLoad == null){
        	setContentView(R.layout.profile_new);
        	save = (Button)this.findViewById(R.id.profile_new_save);
        	
        	nameField = (EditText)this.findViewById(R.id.profile_new_name);
        	emailField = (EditText)this.findViewById(R.id.profile_new_email);
        	addressField = (EditText)this.findViewById(R.id.profile_new_address);
        	phoneField = (EditText)this.findViewById(R.id.profile_new_phone);
        	genderField = (RadioGroup)this.findViewById(R.id.profile_new_genderGroup);
        	
        	save.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					//User info is set in the shared preferences.
					SharedPreferences settings = getSharedPreferences("UserData", 0);
					SharedPreferences.Editor editor = settings.edit();
					
					//get the user's information
					name = nameField.getText().toString();
					email = emailField.getText().toString();
					address = addressField.getText().toString();
					phone = phoneField.getText().toString();
					if(genderField.getCheckedRadioButtonId() == R.id.profile_new_male)
		        		gender = "Male";
		        	else
		        		gender = "Female";
					
					//get the user's id via the http request, and store it in the database
					HttpRequest http = new HttpRequest();
					String uri = "http://spectrackulo.us/350/register.php?name=" + name + 
							"&address=" + address + "&gender=" + gender + "&email=" + email + "&phone=" + phone;
					
					System.out.println(uri);
					String id = http.execHttpRequest(uri, HttpRequest.HttpMethod.Get, "");
					System.out.println(id);
					
					
					//store the information on the device
					editor.putString("Id", id);
					editor.putString("Name", name);
					editor.putString("Address", address);
					editor.putString("Email", email);
					editor.putString("Phone", phone);
					editor.putString("Gender", gender);
					editor.commit();
					
					//let the user know that their information has been saved
					Toast.makeText(ownContext, "Your information has been saved", Toast.LENGTH_SHORT).show();
					finish();
				} 
        	});
        } else {
        	setContentView(R.layout.profile_existing);
        	TextView name = (TextView)this.findViewById(R.id.profile_existing_name);
        	name.setText(_userToLoad.getName());
        	
        	TextView email = (TextView)this.findViewById(R.id.profile_existing_email);
        	email.setText(_userToLoad.getEmail());
        	
        	TextView phone = (TextView)this.findViewById(R.id.profile_existing_phone);
        	phone.setText(_userToLoad.getPhone());
        	
        	TextView address = (TextView)this.findViewById(R.id.profile_existing_address);
        	address.setText(_userToLoad.getAddress());
        	
        	TextView gender = (TextView)this.findViewById(R.id.profile_existing_gender);
        	gender.setText(_userToLoad.getGender());
        }
    }

	@Override
	public void onResume(){
		super.onResume();
        
	}
    
}

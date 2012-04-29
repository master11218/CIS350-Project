package edu.upenn.cis350;

import edu.upenn.cis350.entities.User;
import edu.upenn.cis350.util.InternetHelper;
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

/**
 * Contains information about the user
 * @author DXU
 *
 */
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
	
	/**
	 * Initialize the user's profile on creation
	 */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        _userToLoad = (User)getIntent().getSerializableExtra("user");
        
        //test to see whether this user already has a profile
        if(_userToLoad == null){
        	//this user does  not have a profile, set view to profile_new_save
        	setContentView(R.layout.profile_new);
        	save = (Button)this.findViewById(R.id.profile_new_save);
        	
        	nameField = (EditText)this.findViewById(R.id.profile_new_name);
        	emailField = (EditText)this.findViewById(R.id.profile_new_email);
        	addressField = (EditText)this.findViewById(R.id.profile_new_address);
        	phoneField = (EditText)this.findViewById(R.id.profile_new_phone);
        	genderField = (RadioGroup)this.findViewById(R.id.profile_new_genderGroup);
        	
        	//clicking this button saves the information
        	save.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					saveProfile();
				} 
        	});
        } else {
        	loadProfile();
        }
    }

	@Override
	public void onResume(){
		super.onResume();
        
	}
	
	public void saveProfile() {
		//User info is set in the shared preferences.
		SharedPreferences settings = getSharedPreferences("UserData", 0);
		SharedPreferences.Editor editor = settings.edit();
		
		//Do the error check for input
		name = nameField.getText().toString();
		if (!name.matches("[A-Za-z0-9\\s]+?")){
			displayToast("The name should not be empty and should only contains English characters, numbers or white space");
			return;
		}else{
			name = name.replace(" ", "%20");
		}
		
		email = emailField.getText().toString();
		if (!email.matches("[A-Za-z0-9@]+?")){
			//tell user the input was invalid
			displayToast("The email should not be empty and should only contains English characters, numbers or @");
			return;
		}
		
		address = addressField.getText().toString();
		if (!address.matches("[A-Za-z0-9,\\s]+?")){
			displayToast("The address should not be empty and should only contains English characters, numbers, \",\" or white space");
			return;
		}else{
			address = address.replace(" ", "%20");
		}
		
		phone = phoneField.getText().toString();
		if (!phone.matches("[A-Za-z0-9,\\s]+?")){
			displayToast("The phone should not be empty and should only contains numbers");
			return;
		}
		
		if(genderField.getCheckedRadioButtonId() == R.id.profile_new_male)
    		gender = "Male";
    	else
    		gender = "Female";
		
		//get the user's id via the http request, and store it in the database
		String uri = "http://spectrackulo.us/350/register.php?name=" + name + 
				"&address=" + address + "&gender=" + gender + "&email=" + email + "&phone=" + phone;
		String id = InternetHelper.httpGetRequest(uri);
		
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
	
	public void displayToast(String msg){
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void loadProfile() {
		//this user already has a profile, set view to profile_existing
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

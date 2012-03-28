package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends Activity{

	Spinner parking_spinner;
	Spinner newPatient_spinner;
    Spinner handicap_spinner;
    Spinner creditcard_spinner;
    Spinner providertype_spinner;
    Spinner appointmentonly_spinner;
    EditText provider_name;
    EditText distance;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        //Initialize the spinners
        this.initializeSpinners();
        
        //Initialize the EditText
        this.provider_name = (EditText)findViewById(R.id.search_provider_name);
        this.distance = (EditText)findViewById(R.id.search_distance);
        		
        //create button listener that fetch all the info.
        Button b = (Button) findViewById(R.id.search_button);
        b.setOnClickListener(new searchResultInvoker());
       
    }
	
	@Override
	public void onResume(){
		super.onResume();
	}

	
	/** 
	 * This is the button listener that invokes the search result page.
	 * If the user input contains any error, a toast will be popped up
	 * @author henryou
	 *
	 */
	private class searchResultInvoker implements OnClickListener{

		public void onClick(View arg0) {
			
			Intent i = new Intent(SearchActivity.this, SearchResultActivity.class);
			
			//Pass all the parameters into the intent
			SearchActivity currAct = SearchActivity.this;			
			i.putExtra("provider_name",currAct.provider_name.getText());
			i.putExtra("has_parking", currAct.parking_spinner.getSelectedItem().toString().toLowerCase());
			i.putExtra("accepting_new", currAct.newPatient_spinner.getSelectedItem().toString().toLowerCase());
			i.putExtra("handicap", currAct.handicap_spinner.getSelectedItem().toString().toLowerCase());
			i.putExtra("appointment_only", currAct.appointmentonly_spinner.getSelectedItem().toString().toLowerCase());
			i.putExtra("credit_card", currAct.creditcard_spinner.getSelectedItem().toString().toLowerCase());
			i.putExtra("type", currAct.providertype_spinner.getSelectedItem().toString().toLowerCase());
			i.putExtra("distance", currAct.distance.getText());
			
			//Add more information to the intent
			startActivity(i);
		}
		
	}
	
	/**
	 * Initialize all the spinner in the activity
	 */
	private void initializeSpinners() {
		//The spinner for parking
        parking_spinner = (Spinner) findViewById(R.id.parking_spinner);
        ArrayAdapter<CharSequence> parking_adapter = ArrayAdapter.createFromResource(
                this, R.array.parking_array, android.R.layout.simple_spinner_item);
        parking_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parking_spinner.setAdapter(parking_adapter);
        
        //The spinner for acceptNewPatient
        newPatient_spinner = (Spinner) findViewById(R.id.newpatient_spinner);
        ArrayAdapter<CharSequence> newPatient_adapter = ArrayAdapter.createFromResource(
                this, R.array.newpatient_array, android.R.layout.simple_spinner_item);
        newPatient_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newPatient_spinner.setAdapter(newPatient_adapter);

        //The spinner for handicap accessibility
        handicap_spinner = (Spinner) findViewById(R.id.handicap_spinner);
        ArrayAdapter<CharSequence> handicap_adapter = ArrayAdapter.createFromResource(
                this, R.array.handicap_array, android.R.layout.simple_spinner_item);
        handicap_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        handicap_spinner.setAdapter(handicap_adapter);
        
        //The spinner for accepting credit card
        creditcard_spinner = (Spinner) findViewById(R.id.creditcard_spinner);
        ArrayAdapter<CharSequence> creditcard_adapter = ArrayAdapter.createFromResource(
                this, R.array.creditcard_array, android.R.layout.simple_spinner_item);
        creditcard_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        creditcard_spinner.setAdapter(creditcard_adapter);
        
        //The spinner for appointment only
        appointmentonly_spinner = (Spinner) findViewById(R.id.appointmentonly_spinner);
        ArrayAdapter<CharSequence> appointmentonly_adapter = ArrayAdapter.createFromResource(
                this, R.array.appointmentonly_array, android.R.layout.simple_spinner_item);
        appointmentonly_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentonly_spinner.setAdapter(appointmentonly_adapter);
        
        //The spinner for provider type
        providertype_spinner = (Spinner) findViewById(R.id.providertype_spinner);
        ArrayAdapter<CharSequence> providertype_adapter = ArrayAdapter.createFromResource(
                this, R.array.providertype_array, android.R.layout.simple_spinner_item);
        providertype_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providertype_spinner.setAdapter(providertype_adapter);
	}


  
}

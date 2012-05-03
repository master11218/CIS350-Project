package edu.upenn.cis350;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This is the page that allows the user to perform search on providers. The
 * user can search the provider by keyword and filter the result by other
 * criteria
 * 
 * @author henryou
 * 
 */
public class SearchActivity extends Activity {
	Spinner parking_spinner;
	Spinner newPatient_spinner;
	Spinner handicap_spinner;
	Spinner creditcard_spinner;
	Spinner providertype_spinner;
	Spinner appointmentonly_spinner;
	EditText provider_name;
	EditText distance;

	/**
	 * Set up the Activity on creation
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		// Initialize the spinners for the filter criteria
		this.initializeSpinners();

		// Initialize the EditText for search by distance and search by provider
		// name
		this.provider_name = (EditText) findViewById(R.id.search_provider_name);
		this.distance = (EditText) findViewById(R.id.search_distance);

		// create button listener that fetch all the info.
		Button b = (Button) findViewById(R.id.search_button);
		b.setOnClickListener(new searchResultInvoker());

	}

	/**
	 * Set up the Activity when bringing up the Search Activity
	 */
	public void onResume() {
		super.onResume();
	}

	/**
	 * Initialize all the spinners in the activity
	 */
	private void initializeSpinners() {
		// The spinner for parking
		this.parking_spinner = this.createSpinner(R.id.parking_spinner,
				R.array.parking_array);

		// The spinner for acceptNewPatient
		this.newPatient_spinner = this.createSpinner(R.id.newpatient_spinner,
				R.array.newpatient_array);

		// The spinner for handicap accessibility
		this.handicap_spinner = this.createSpinner(R.id.handicap_spinner,
				R.array.handicap_array);

		// The spinner for accepting credit card
		this.creditcard_spinner = this.createSpinner(R.id.creditcard_spinner,
				R.array.creditcard_array);

		// The spinner for appointment only
		this.appointmentonly_spinner = this.createSpinner(
				R.id.appointmentonly_spinner, R.array.appointmentonly_array);

		// The spinner for provider type
		this.providertype_spinner = this.createSpinner(
				R.id.providertype_spinner, R.array.providertype_array);
	}

	/**
	 * This method retrieves a spinner by a spinnerId and set the adapter of
	 * that spinner with the array from the choiceArrayId
	 * 
	 * @param spinnerId
	 *            The id of the spinner to retrieve
	 * @param choiceArrayId
	 *            The id of the array of the choice array
	 * @return
	 */
	private Spinner createSpinner(int spinnerId, int choiceArrayId) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, choiceArrayId, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		return spinner;
	}

	/**
	 * This is the button listener that invokes the search result page. It
	 * passes all the user criteria to the SearchResultActivity
	 */
	private class searchResultInvoker implements OnClickListener {

		public void onClick(View arg0) {
			
			String providerNameStr =  getEditTextEntry(provider_name);
			//make sure the input for keyword search is correct
			if (providerNameStr.length()>0 && !providerNameStr.matches("[A-Za-z0-9\\s&&[^\\n]]+?")){
				//tell user the input was invalid
				Context context = getApplicationContext();
				Toast toast = Toast.makeText(context, "The keyword for search should only contains" +
						" English characters, numbers or white space",Toast.LENGTH_SHORT);
				toast.show();
				return;
			}else{
				
				providerNameStr = providerNameStr.replace(" ", "%20");
			}
			
			Intent i = new Intent(SearchActivity.this, SearchResultActivity.class);
			
			//Pass all the parameters into the intent			
			i.putExtra("provider_name",providerNameStr);
			i.putExtra("has_parking", getSpinnerSelection(parking_spinner));
			i.putExtra("accepting_new", getSpinnerSelection(newPatient_spinner));
			i.putExtra("handicap", getSpinnerSelection(handicap_spinner));
			i.putExtra("appointment_only", getSpinnerSelection(appointmentonly_spinner));
			i.putExtra("credit_card", getSpinnerSelection(creditcard_spinner));
			i.putExtra("type", getSpinnerSelection(providertype_spinner));
			i.putExtra("distance", getEditTextEntry(distance));
			
			//Add more information to the intent
			startActivity(i);
		}

		/**
		 * Return the text of the selected item in the spinner Note that for
		 * consistency all the text is converted to lowercase
		 * 
		 * @param spinner
		 * @return
		 */
		private String getSpinnerSelection(Spinner spinner) {
			return spinner.getSelectedItem().toString().toLowerCase();
		}

		/**
		 * Return the text of the EditText
		 * 
		 * @param input
		 * @return
		 */
		private String getEditTextEntry(EditText input) {
			return input.getText().toString();
		}
	}
}

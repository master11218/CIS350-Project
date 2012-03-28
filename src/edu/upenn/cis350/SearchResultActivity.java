package edu.upenn.cis350;

import java.util.ArrayList;

import edu.upenn.cis350.util.ProviderHelper;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SearchResultActivity extends Activity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        //TODO: change this
        setContentView(R.layout.search);
        
        /*
        Bundle extras = getIntent().getExtras(); 

        if (extras != null) {
			String provider_name = extras.getString("provider_name");
			String has_parking = extras.getString("has_parking");
			String accepting_new = extras.getString("accepting_new");
			String handicap = extras.getString("handicap");
			String appointment_only = extras.getString("appointment_only");
			String credit_card = extras.getString("credit_card");
			String type = extras.getString("type");
			String distance = extras.getString("distance");
	     
	        ArrayList<Provider> satisfied = ProviderHelper.getSatisfiedProvider(provider_name, has_parking, accepting_new,
	        		handicap,appointment_only,credit_card,type,distance); 
	        
	        
	        
	
	       
	        //TODO: Get the current location
	        
	        
	        //TODO:Change the value of the type		
			String providerType = currAct.providertype_spinner.getSelectedItem().toString().toLowerCase();
			
			providerType = providerType.equals("primary care")? "PCP":"Specialist";
			
        }
        */
    }
	
	
}

package edu.upenn.cis350;

import android.app.Activity;
import android.os.Bundle;

public class ProviderProfileActivity extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_pf);
        
        //initialize a dummy provider.
        Provider p = new Provider();
        
    }

	@Override
	public void onResume(){
		super.onResume();
        
	}
    
}

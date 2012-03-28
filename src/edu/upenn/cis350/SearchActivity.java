package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends Activity{
	
	private Button temp;
	private final Context m_context = this;
	private ArrayList<Provider> m_providers = new ArrayList<Provider>();
	private ListView m_results;
	
	public Provider generateProvider(String name, double latitude, double longitude){
		Rating first = new Rating(3,1,new Date(System.currentTimeMillis()), "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
				5);
		Rating second = new Rating(4,1,new Date(System.currentTimeMillis()), " adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehend",
				3);
		Rating third = new Rating(5,1,new Date(System.currentTimeMillis()), " This guy is awesome!!!!!!!!!!!!!!!!!!!!!!!",
				3);
		Rating fourth= new Rating(5,1,new Date(System.currentTimeMillis()), " I don't speak latin );",
				4);
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		ratings.add(first);
		ratings.add(second);
		ratings.add(third); 
		ratings.add(fourth);
		
		//initialize a dummy provider.
		return new Provider(1, name, "3400 Spruce Street", "Philadelphia", "PA", "19104", "(215)662-3228", "yes", "no", "PCP", "no", "yes", ratings, latitude, longitude);
	}
	
	public void generateProviderList(){

        //Generate a bunch of temporary random providers
        m_providers.add(generateProvider("Loraine Zachery", 39.951481, -75.200987));
        m_providers.add(generateProvider("Tyrone Bolan", 39.952481, -75.200987));
        m_providers.add(generateProvider("Roslyn Chico", 39.953481, -75.200987));
        m_providers.add(generateProvider("Sharron Becher", 39.954481, -75.200987));
        m_providers.add(generateProvider("Steidl Zachery", 39.955481, -75.200987));
        m_providers.add(generateProvider("Hugh Tandy", 39.956481, -75.200987));
        m_providers.add(generateProvider("Mathew Dimas", 39.957481, -75.200987));
        m_providers.add(generateProvider("Milagros Siegmund", 39.958481, -75.200987));
        m_providers.add(generateProvider("Serena Champine", 39.959481, -75.200987));
        m_providers.add(generateProvider("Allie Lunday", 39.950481, -75.200987));
        
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        m_results = (ListView)this.findViewById(R.id.search_results);
        m_results.setAdapter(new SearchAdapter(this));
        
        //temporarily generate data
        generateProviderList();
        
        Spinner spinner= (Spinner) findViewById(R.id.search_provider_spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
        		this, R.array.provider_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
    }

	@Override
	public void onResume(){
		super.onResume();
		
		
		
	}
	
	
	

	class SearchAdapter extends BaseAdapter{
		private Context m_context;
		public SearchAdapter(Context c){
			m_context = c;
		}
		public int getCount() {
			if(m_providers != null)
				return m_providers.size();
			else
				return 0;
		}
		public Object getItem(int position) {
			if(m_providers != null)
				return m_providers.get(position);
			else
				return 0;
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(final int position, View convertView, ViewGroup parent) {

			//generate the new layout for each provider
	        RelativeLayout list_result;
	        if(convertView == null){
	        	LayoutInflater inf = (LayoutInflater)m_context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				list_result = (RelativeLayout) inf.inflate(R.layout.search_result, null);
				
	        }
	        else
	        	list_result = (RelativeLayout)convertView;
	        
	        //populate the inflated view.
	        TextView tv_name = (TextView)list_result.findViewById(R.id.search_result_rating);
	        Double temp = m_providers.get(position).getAvgRating();
	        tv_name.setText("Rating: " + temp.toString());
	        
	        TextView tv_provider_desc = (TextView)list_result.findViewById(R.id.search_result_name);
	        tv_provider_desc.setText(m_providers.get(position).getName());
	        

	        Button b = (Button)list_result.findViewById(R.id.search_result_button);
	        b.setOnClickListener(new OnClickListener(){
				
				public void onClick(View arg0) {
					Intent intent = new Intent(m_context, ProviderProfileActivity.class);
					intent.putExtra("providers", m_providers.get(position));
					for(Rating s: m_providers.get(position).getRatings()){
						System.out.println("hihuhiuhiuh");
						System.out.println(s.getReview());
					}
					startActivity(intent);
				}
	        });
	        		
			return list_result;
		}
	}
    
}

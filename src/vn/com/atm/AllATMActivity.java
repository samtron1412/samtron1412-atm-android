package vn.com.atm;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;

import vn.com.atm.database.JSONParser;



import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AllATMActivity extends ListActivity{
	private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
   
    ArrayList<HashMap<String, String>> atmList;
 
    // url to get all products list
   
    // JSON Node names
 

 
    // products JSONArray
    JSONArray atm = null;
    ListView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	
    	atmList = new ArrayList<HashMap<String, String>>();
    	setContentView(R.layout.activity_all_atm);
    	
    	new LoadATMList().execute();
    	SearchActivity.adapter.notifyDataSetChanged(); 
    }
    
    class LoadATMList extends AsyncTask<String, String, String>
    {
    	 @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(AllATMActivity.this);
             pDialog.setMessage("Loading data. Please wait...");
             pDialog.setIndeterminate(false);
             pDialog.setCancelable(false);
             pDialog.show();
         }
    	@Override
		protected String doInBackground(String...  args) {
             // getting JSON string from URL
    	/*	lv =(ListView)findViewById(android.R.id.list);
    		lv.setAdapter(SearchActivity.adapter);*/
            return null;
		}
    	 protected void onPostExecute(String file_url) {
             // dismiss the dialog after getting all products
             pDialog.dismiss();
             // updating UI from Background Thread
             runOnUiThread(new Runnable() {
                 public void run() {
                     /**
                      * Updating parsed JSON data into ListView
                      * */
                    /* ListAdapter adapter = new SimpleAdapter(
                             AllATMActivity.this, atmList,
                             R.layout.list_item, new String[] { TAG_BANKCODE,
                            		 TAG_ADDRESS},
                             new int[] { R.id.pid, R.id.name });
                     // updating listview
                     setListAdapter(adapter);*/
                	lv =(ListView)findViewById(android.R.id.list);
             		lv.setAdapter(SearchActivity.adapter);
                 }
             });
  
         }
    }
    
    
		
}

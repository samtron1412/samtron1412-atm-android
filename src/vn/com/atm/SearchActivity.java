package vn.com.atm;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import vn.com.atm.database.UserFunctions;
import vn.com.atm.obj.AtmObj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends Activity{
	
	private Spinner spnBank;
	private Button btnViewMap,btnSearch, btnCancel;
	private EditText edAtmCode, edAddress;
	private static final String TAG_BANK = "bank";
	private static final String TAG_BANK_NAME = "bankName";
	private static final String TAG_ATM = "atm";
	
	private static final String TAG_BANK_CODE = "bankCode", TAG_ATM_CODE = "atmCode", TAG_ADDRESS = "address";
	String bankCode, atmCode, address;
	JSONArray atm;
	// Hien thi list view
	public static ArrayList<AtmObj> arrayregister;
	public static AdapterRegister adapter;
	JSONArray arrBank;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 UserFunctions userFunction = new UserFunctions();
	     JSONObject json = userFunction.listBank();
	     
	   
	     
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search);
		
		spnBank = (Spinner)findViewById(R.id.spnBank);
		
		
		 //Create array
        //ArrayAdapter<CharSequence> adapBank = ArrayAdapter.createFromResource(SearchActivity.this, R.array.list_bank, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapBank = new ArrayAdapter<CharSequence>(SearchActivity.this, android.R.layout.simple_spinner_item); 
        adapBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set adapter
        
        spnBank.setAdapter(adapBank);
        //Set selected Listener
        
        try {
			if(json.getString(TAG_BANK)!= null)
			 {
				arrBank = json.getJSONArray(TAG_BANK);
				for(int i = 0; i < arrBank.length(); i ++)
				{
					JSONObject ojbBank = arrBank.getJSONObject(i);
					adapBank.add(ojbBank.getString(TAG_BANK_NAME));
				}
			 }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        spnBank.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adap, View v,
					int position, long arg3) {
				
				bankCode = adap.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        //Button view map
        
        btnViewMap = (Button)findViewById(R.id.btnViewMap);
        
        btnViewMap.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent i = new Intent(SearchActivity.this,MapViewActivity.class);
				startActivity(i);
				finish();
			}
		});
        
        // Button search
        arrayregister = new ArrayList<AtmObj>(); 
	    adapter = new AdapterRegister(this,R.layout.atm_item, arrayregister);
       
	    btnSearch = (Button)findViewById(R.id.btnSearch);
        edAtmCode = (EditText)findViewById(R.id.edAtmCode);
        edAddress = (EditText)findViewById(R.id.edAddress);
        btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				address = edAddress.getText().toString();
				atmCode = edAtmCode.getText().toString();
				
				 // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                		AllATMActivity.class);
                // sending pid to next activity
               UserFunctions userFunction = new UserFunctions();
       		 
        		
      		  
                JSONObject json = userFunction.searchATM(bankCode, atmCode, address, 0);
                
                atm = new JSONArray();
                // Check your log cat for JSON reponse
                Log.d("All ATM: ", json.toString());
     
                try {
                    // Checking for SUCCESS TAG
                    
     
                    if (json.getJSONArray(TAG_ATM) != null) {
                        // products found
                        // Getting Array of Products
                        atm = json.getJSONArray(TAG_ATM);
     
                        // looping through All Products
                        for (int i = 0; i < atm.length(); i++) {
                            JSONObject c = atm.getJSONObject(i);
     
                            // Storing each json item in variable
                            String bankCode = "Mã máy: " + c.getString(TAG_ATM_CODE);
                            String address = "Tên ngân hàng: "+ c.getString(TAG_BANK_CODE);
                            String atmCode = "Địa chỉ: " + c.getString(TAG_ADDRESS);
                            // creating new HashMap
                            /*HashMap<String, String> map = new HashMap<String, String>();
     
                        
                            JSONObject js = userFunction.getImg();
                            String strbm = js.getString("atmImage");*/
                      
                            // adding HashList to ArrayList
                            AtmObj item = new AtmObj(atmCode, bankCode, address, null,null, null); 
                            
                            arrayregister.add(0, item); 
                       
                        }
                    } else {
                        // no products found
                        // Launch Add New product Activity
                        Intent i = new Intent(getApplicationContext(),
                                MainActivity.class);
                        // Closing all previous activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
     
                startActivity(in);
                finish();
			}
		});
        
        // Button Cancel
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getBaseContext(),MainActivity.class);
				startActivity(i);
				
				finish();
			}
		});
        
	}
	
	
	  public class AdapterRegister extends ArrayAdapter<AtmObj>{
	    	Context mcontext;
	    	int rource;
	    	ArrayList<AtmObj> array;
	    	
	    	public AdapterRegister(Context context, int textViewResourceId,	ArrayList<AtmObj> arrays) {
				super(context, textViewResourceId, arrays);
				
				this.mcontext = context;
				this.array = arrays;
				this.rource = textViewResourceId;
			}

	    	@Override
	    	public View getView(int position, View convertView, ViewGroup parent) {
	    		View view = convertView;
	    		AtmObj item;
	    		
	    		if(view==null){
	    			view = getLayoutInflater().inflate(R.layout.atm_item, null);
	    		}
	    		if((item=this.array.get(position))==null){
	    			return view;
	    		}
	    		
	    		ImageView icon = (ImageView)view.findViewById(R.id.icon);
	    		TextView atmName = (TextView)view.findViewById(R.id.atmName);
	    		TextView bankName = (TextView)view.findViewById(R.id.bankName);
	    		TextView address = (TextView)view.findViewById(R.id.address);
	    		
	    		icon.setImageBitmap(item.bm);
	    		atmName.setText(item.atmCode);
	    		bankName.setText(item.bankCode);
	    		address.setText(item.address);
	    		
	    		return view;
	    	}

	    }


}

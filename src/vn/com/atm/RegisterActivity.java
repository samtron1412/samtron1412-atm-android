package vn.com.atm;


import org.json.JSONException;
import org.json.JSONObject;

import vn.com.atm.database.UserFunctions;
import vn.com.atm.library.SimpleCrypto;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    Button btnRegister;
    TextView btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;
    private ProgressDialog pDialog;
    // JSON Response node names
    private static String KEY_SUCCESS = "status";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
 
        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.reg_fullname);
        inputEmail = (EditText) findViewById(R.id.reg_email);
        inputPassword = (EditText) findViewById(R.id.reg_password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (TextView) findViewById(R.id.link_to_login);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
 
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	 new Register().execute();
            }
            
            
        });
       
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this); 
                builder.setTitle("Info success"); 
                builder.setMessage("Account Register success"); 
                
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() { 
                    public void onClick(DialogInterface dialog, int which) { 
                            }                         
                          });
                builder.show(); 
            	Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            	startActivity(i);
            }
        });
    }
    
    public class Register extends AsyncTask<String, String, String>{
    	@Override
    	  protected  void onPreExecute() {
    	        super.onPreExecute();
    	        pDialog = new ProgressDialog(RegisterActivity.this);
    	        pDialog.setMessage("Loading data. Please wait...");
    	        pDialog.setIndeterminate(false);
    	        pDialog.setCancelable(false);
    	        pDialog.show();
    	    }
    	 
		@Override
		protected String doInBackground(String... params) {
			String name = inputFullName.getText().toString();
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            SimpleCrypto sp = new SimpleCrypto();
            try {
          
			 password = sp.encrypt("key", password);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.registerUser(name, email, password);

            // check for login response
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                   // registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);
                    if(res.equalsIgnoreCase("SUCCESS")== true){
                        // user successfully registred
                        // Store user details in SQLite Database
                        /*DatabaseHandler db = new DatabaseHandler(getApplicationContext());*/
                       /* JSONObject json_user = json.getJSONObject("user");*/

                        // Clear all previous data in database
                        /*userFunction.logoutUser(getApplicationContext());
                        db.addUser(name, email, password);*/
                        // Launch Dashboard Screen
                    	 /*Register rg = new Register();*/
                    	
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        // Close all views before launching Dashboard
                        /*login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                        
                        startActivity(login);
                        // Close Registration Screen
                       
                        finish();
                    }else{
                        // Error in registration
                        registerErrorMsg.setText("Error occured in registration");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
			return null;
		}
		 /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
		
  
    }
   
}
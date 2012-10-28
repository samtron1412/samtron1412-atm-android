package vn.com.atm;


import org.json.JSONException;
import org.json.JSONObject;

import vn.com.atm.database.UserFunctions;
import vn.com.atm.library.SimpleCrypto;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

 
public class LoginActivity extends Activity {
    Button btnLogin;
    TextView btnLinkToRegister;
    EditText inputName;
    EditText inputPassword;
    TextView loginErrorMsg;
 
    // JSON Response node names
    private static String KEY_SUCCESS = "status";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
 
        // Importing all assets like buttons, text fields
        inputName = (EditText) findViewById(R.id.lg_Account);
        inputPassword = (EditText) findViewById(R.id.lg_pass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (TextView) findViewById(R.id.link_to_register);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String userName = inputName.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                SimpleCrypto sp = new SimpleCrypto();
                try {	 
                	password = sp.encrypt("key", password);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                JSONObject json = userFunction.loginUser(userName, password);
 
                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(res.equalsIgnoreCase("SUCCESS") == true){
                            // user successfully logged in
                            // Store user details in SQLite Database
                            /*DatabaseHandler db = new DatabaseHandler(getApplicationContext());*/
                           /* JSONObject json_user = json.getJSONObject("userLogin");*/
 
                            // Clear all previous data in database
                           /* userFunction.logoutUser(getApplicationContext());
                            try {
								password = sp.decrypt("key", password);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
                            /*db.addUser(userName,"@gmail.com", password);  */                     
 
                            // Launch Dashboard Screen
                        	
                        	//Alert success
                        	AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); 
                            builder.setTitle("Info success"); 
                            builder.setMessage("Account login success"); 
                            
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() { 
                                public void onClick(DialogInterface dialog, int which) { 
                                        }                         
                                      });
                            builder.show(); 
                            
                            
                            
                            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
 
                            // Close all views before launching Dashboard
                            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mainActivity.putExtra("Status", "Success");
                            
                            startActivity(mainActivity);
                            
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
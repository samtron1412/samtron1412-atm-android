package vn.com.atm.database;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

 
import android.content.Context;
 
public class UserFunctions {
 
    private JSONParser jsonParser;
 
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://14.160.91.174:8383/atm-web/atm/userLogin.json";
    private static String registerURL = "http://14.160.91.174:8383/atm-web/atm/userRegister.json";
    private static String listATMURL = "http://14.160.91.174:8383/atm-web/atm/atmList.json";
    private static String listBankURL = "http://14.160.91.174:8383/atm-web/atm/bankList.json";
    private static String listSearchURL = "http://14.160.91.174:8383/atm-web/atm/atmSearch.json";
    private static String imgURL = "http://14.160.91.174:8383/atm-web/atm/atmImage.json";
    
    private static String ROUTE_URL= "";
    private static String login_tag = "login";
    private static String register_tag = "register";
 
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
 
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String userName, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("userName", userName));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
 
   
    
    /**
     * function make Register Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("userName", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
 
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }
    /**
     * function make getBank Request
     * */
    public JSONObject listBank(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
 
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(listBankURL, params);
        // return json
        return json;
    }
    /**
     * function make getATM Request
     * */
    public JSONObject listATM(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
 
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(listATMURL, params);
        // return json
        return json;
    }
    public JSONObject getImg(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
 
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(listATMURL, params);
        // return json
        return json;
    }
    public JSONObject searchATM(String bankCode, String atmCode, String address, int isBrank){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("bankCode", bankCode));
        params.add(new BasicNameValuePair("atmCode", atmCode));
        params.add(new BasicNameValuePair("address", address));
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(listSearchURL, params);
        // return json
        return json;
    }
    
    public JSONObject getPoints(double latSr, double longSr, double latDes, double longDes){
        // Building Parameters
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	ROUTE_URL = "http://maps.googleapis.com/maps/api/directions/json?origin="+ latSr +","+ longSr +"&destination="+ latDes +","+ longDes +"&sensor=false";
    	JSONObject json = jsonParser.getJSONFromUrl(ROUTE_URL, params);
       
        return json;
    }
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
 
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
 
}
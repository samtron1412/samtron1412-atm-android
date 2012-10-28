package vn.com.atm.database;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "atm_service";
 
    // Login table name
    private static final String TABLE_USER = "user";
    // Login Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_NAME + " TEXT PRIMARY KEY,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASS + " TEXT"+ ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_PASS, pass); // Email
 
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }
 
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("pass", cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
 
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();
    }
 
}

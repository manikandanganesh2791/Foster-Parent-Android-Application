package com.example.gkart.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by gkart on 2/24/2017.
 */

public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "login.db";
    private static final String TABLE_LOGIN = "LOGIN";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "FIRSTNAME text,LASTNAME text,AGE text, " +
            "ADDRESSONE text,ADDRESSTWO text,CITY text,STATE text,ZIP text,USERNAME  text,PASSWORD text,EMAIL text,PHONE text,REGISTERID text); ";
    static final String DATABASE_CREATE_LOGIN = "create table "+"USER_LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USER_NAME text,STORED_PASSWORD text); ";
    static final String DATABASE_REGISTER = "create table "+"REGISTRATION"+
            "( " +"ID"+" integer primary key autoincrement,"+ "FIRSTNAME text,LASTNAME text,AGE text, " +
            "ADDRESSONE text,ADDRESSTWO text,CITY text,STATE text,ZIP text,USERNAME  text,PASSWORD text,EMAIL text,PHONE text,REGISTERID text," +
            "LATITUDE double, LONGITUDE double); ";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DatabaseHelper dbHelper;
    public LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String firstName, String lastName, String age, String addressOne, String addressTwo,
                            String city, String state, String zip, String userName,String password, String email, String phone,
                            String regID)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("FIRSTNAME", firstName);
        newValues.put("LASTNAME", lastName);
        newValues.put("AGE", age);
        newValues.put("ADDRESSONE", addressOne);
        newValues.put("ADDRESSTWO", addressTwo);
        newValues.put("CITY", city);
        newValues.put("STATE", state);
        newValues.put("ZIP", zip);
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);
        newValues.put("EMAIL", email);
        newValues.put("PHONE", phone);
        newValues.put("REGISTERID", regID);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insertRegisterEntry(ArrayList<String> userCredentials)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("FIRSTNAME", userCredentials.get(0));
        newValues.put("LASTNAME", userCredentials.get(1));
        newValues.put("AGE", userCredentials.get(2));
        newValues.put("ADDRESSONE", userCredentials.get(3));
        newValues.put("ADDRESSTWO", userCredentials.get(4));
        newValues.put("CITY", userCredentials.get(5));
        newValues.put("STATE", userCredentials.get(6));
        newValues.put("ZIP", userCredentials.get(7));
        newValues.put("USERNAME", userCredentials.get(8));
        newValues.put("PASSWORD",userCredentials.get(9));
        newValues.put("EMAIL", userCredentials.get(10));
        newValues.put("PHONE", userCredentials.get(11));
        newValues.put("REGISTERID", userCredentials.get(12));
        newValues.put("LATITUDE", Double.parseDouble(userCredentials.get(13)));
        newValues.put("LONGITUDE", Double.parseDouble(userCredentials.get(14)));


        // Insert the row into your table
        db.insert("REGISTRATION", null, newValues);
        db.close();
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public void insertUserEntry(String userName, String storedPassword){
        ContentValues newValues = new ContentValues();
        newValues.put("USER_NAME", userName);
        newValues.put("STORED_PASSWORD",storedPassword);
        db.insert("USER_LOGIN", null, newValues);
    }

    public void  updateEntry(String firstName, String lastName, String age, String addressOne, String addressTwo, String city,
                             String state, String zip, String userName,String password, String email, String phone, String registerID)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("FIRSTNAME", firstName);
        updatedValues.put("LASTNAME", lastName);
        updatedValues.put("AGE", age);
        updatedValues.put("ADDRESSONE", addressOne);
        updatedValues.put("ADDRESSTWO", addressTwo);
        updatedValues.put("CITY", city);
        updatedValues.put("STATE", state);
        updatedValues.put("ZIP", zip);
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);
        updatedValues.put("EMAIL", email);
        updatedValues.put("PHONE", phone);
        updatedValues.put("REGISTERID", registerID);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }

    public ArrayList<String> getFirstName() {
        String userName = "";
        ArrayList<String> user_Details = new ArrayList<String>();
        StringBuilder lastName = new StringBuilder();
        int i = 1;
        SQLiteDatabase db = this.getDatabaseInstance();
        String selectQuery = "select USER_NAME from USER_LOGIN WHERE ID = (SELECT MAX(ID) FROM USER_LOGIN);";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                lastName.append(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();
        userName = lastName.toString();
        String selectUserDetails = "select * from LOGIN WHERE USERNAME = " + "\'" + userName + "\'" + ";";
        Cursor userCursor = db.rawQuery(selectUserDetails,null);
        userCursor.moveToFirst();
        while(i < userCursor.getColumnCount()){
            user_Details.add(userCursor.getString(i));
            Log.d("Cursor i Value", userCursor.getString(i));
            i++;
        }
        userCursor.close();
        return user_Details;
    }

    public String getUserAddress(){
        String userName = "";
        StringBuilder lastName = new StringBuilder();
        String city_val = "";
        SQLiteDatabase db = this.getDatabaseInstance();
        String selectQuery = "select USER_NAME from USER_LOGIN WHERE ID = (SELECT MAX(ID) FROM USER_LOGIN);";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                lastName.append(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();
        userName = lastName.toString();
        String selectUserDetails = "select CITY FROM LOGIN WHERE USERNAME = " + "\'" + userName + "\'" + ";";
        Cursor userCursor = db.rawQuery(selectUserDetails,null);
        userCursor.moveToFirst();
        city_val = userCursor.getString(0);
        userCursor.close();
        return city_val;
    }


    public String getAddress() {
        String userName = "";
        ArrayList<String> addresslist = new ArrayList<String>();
        String user_Address = "";
        StringBuilder lastName = new StringBuilder();
        int i = 0;
        SQLiteDatabase db = this.getDatabaseInstance();
        String selectQuery = "select USER_NAME from USER_LOGIN WHERE ID = (SELECT MAX(ID) FROM USER_LOGIN);";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                lastName.append(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();
        userName = lastName.toString();
        String selectUserDetails = "select ADDRESSONE, ADDRESSTWO, CITY, STATE, ZIP from LOGIN WHERE USERNAME = " + "\'" + userName + "\'" + ";";
        Cursor userCursor = db.rawQuery(selectUserDetails,null);
        userCursor.moveToFirst();
        while(i < userCursor.getColumnCount()){
            addresslist.add(userCursor.getString(i));
            i++;
        }
        userCursor.close();
        for(String addressval : addresslist){
            user_Address = addressval + " ";
        }
        return user_Address;
    }

    public ArrayList<String> getUserCredentials(String useraddress) {
        ArrayList<String> user_Details = new ArrayList<String>();
        SQLiteDatabase db = this.getDatabaseInstance();
        String selectQuery = "select FIRSTNAME, LASTNAME, PHONE from REGISTRATION WHERE REGISTRATION.CITY = " +
                "\'" + useraddress + "\'" + ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        int i = 0;
        while(i < cursor.getColumnCount()){
            user_Details.add(cursor.getString(i));
            i++;
        }
        cursor.close();
        return user_Details;
    }
}

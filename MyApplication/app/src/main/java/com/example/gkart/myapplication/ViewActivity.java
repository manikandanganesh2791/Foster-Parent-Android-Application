package com.example.gkart.myapplication;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gkart on 2/25/2017.
 */

public class ViewActivity extends AppCompatActivity{
    String address = "";
    String useraddress = "";
    LoginDataBaseAdapter loginDataBaseAdapter;
    ArrayList<String> user_Credentials = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewactivity);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        address = loginDataBaseAdapter.getAddress();
        useraddress = loginDataBaseAdapter.getUserAddress();
        Geocoder geocoder = new Geocoder(this);
        try{
            ArrayList<Address> address_val =  (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
            for(Address add : address_val){
                double latitude = add.getLatitude();
                double longitude = add.getLongitude();
            }
        } catch (IOException io){
            io.printStackTrace();
        }
        user_Credentials.addAll(loginDataBaseAdapter.getUserCredentials(useraddress));
        populateListView(user_Credentials);
    }
    private void populateListView(ArrayList<String> user_Credentials){
        String[] myitems = {user_Credentials.get(0) + " " + user_Credentials.get(1) + " " + user_Credentials.get(2)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.daitem, myitems);
        ListView listView = (ListView) findViewById(R.id.listmain);
        listView.setAdapter(adapter);
    }
}

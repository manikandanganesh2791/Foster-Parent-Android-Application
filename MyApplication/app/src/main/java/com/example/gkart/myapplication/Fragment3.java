package com.example.gkart.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.checked;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {

    int distance_val = 0;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String address = "";
    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginDataBaseAdapter = new LoginDataBaseAdapter(getActivity());
        loginDataBaseAdapter = loginDataBaseAdapter.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity activity = getActivity();
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_fragment3, container, false);
        TextView textview = (TextView) result.findViewById(R.id.registration_id);
        RadioButton button1 = (RadioButton) result.findViewById(R.id.radio_miles_five);
        RadioButton button2 = (RadioButton) result.findViewById(R.id.radio_miles_ten);
        RadioGroup radiogroup = (RadioGroup) result.findViewById(R.id.radio_group);
        Button button = (Button) result.findViewById(R.id.registrationButton);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_miles_five:
                        distance_val = 5;
                        break;
                    case R.id.radio_miles_ten:
                        distance_val = 10;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> userCredentials = new ArrayList<String>();
                userCredentials.addAll(loginDataBaseAdapter.getFirstName());
                address = userCredentials.get(3) + " " + userCredentials.get(4) + " " + userCredentials.get(5) +
                        " " + userCredentials.get(6) + " " + userCredentials.get(7);
                Geocoder geocoder = new Geocoder(getActivity());
                try{
                    ArrayList<Address> address_val =  (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
                    for(Address add : address_val){
                        double latitude = add.getLatitude();
                        userCredentials.add(String.valueOf(latitude));
                        double longitude = add.getLongitude();
                        userCredentials.add(String.valueOf(longitude));
                    }
                } catch (IOException io){
                    io.printStackTrace();
                }
                loginDataBaseAdapter.insertRegisterEntry(userCredentials);
                Toast.makeText(getActivity(), "Registered...", Toast.LENGTH_SHORT).show();
                Intent lIntent = new Intent();
                lIntent.setClass(getActivity(), MainActivity.class);
                startActivity(lIntent);
            }
        });
        return (result);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

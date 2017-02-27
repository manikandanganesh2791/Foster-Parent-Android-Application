package com.example.gkart.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by gkart on 2/24/2017.
 */

public class SignUpActivity extends Activity {
    EditText editFirstName, editLastName, editAge, editAddressOne, editAddressTwo, editCity, editState, editZip, editUserName,
            editPassword, editEmail, editPhone, editRegistrationID;
    Button btnCreateAccount;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editFirstName=(EditText)findViewById(R.id.editFirstName);
        editLastName=(EditText)findViewById(R.id.editLastName);
        editAge=(EditText)findViewById(R.id.editAge);
        editAddressOne=(EditText)findViewById(R.id.editAddressOne);
        editAddressTwo=(EditText)findViewById(R.id.editAddressTwo);
        editCity=(EditText)findViewById(R.id.editCity);
        editState=(EditText)findViewById(R.id.editState);
        editZip=(EditText)findViewById(R.id.editZip);
        editUserName=(EditText)findViewById(R.id.editUser);
        editPassword=(EditText)findViewById(R.id.editPassword);
        editEmail=(EditText)findViewById(R.id.editUser);
        editPhone=(EditText)findViewById(R.id.editPhone);
        editRegistrationID=(EditText)findViewById(R.id.editRegister);

        btnCreateAccount=(Button)findViewById(R.id.registerButton);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String firstName=editFirstName.getText().toString();
                String lastName=editLastName.getText().toString();
                String age = editAge.getText().toString();
                String addresslineone = editAddressOne.getText().toString();
                String addresslinetwo = editAddressTwo.getText().toString();
                String city = editCity.getText().toString();
                String state = editState.getText().toString();
                String zip = editZip.getText().toString();
                String userName=editEmail.getText().toString();
                String password=editPassword.getText().toString();
                String email = editEmail.getText().toString();
                String phone=editPhone.getText().toString();
                String registerId = editRegistrationID.getText().toString();

                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||firstName.equals("")|| lastName.equals("")||phone.equals("")||
                        addresslineone.equals("")||addresslinetwo.equals("")||city.equals("")|| state.equals("")||zip.equals("")||
                        email.equals("")||registerId.equals("")||age.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(firstName, lastName, age, addresslineone,
                            addresslinetwo, city, state, zip, userName, password, email, phone, registerId);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Intent lIntent = new Intent();
                    lIntent.setClass(v.getContext(), Login.class);
                    startActivity(lIntent);
                    finish();
                }
            }
        });


    }
}
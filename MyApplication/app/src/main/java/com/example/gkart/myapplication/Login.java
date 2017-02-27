package com.example.gkart.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by gkart on 2/24/2017.
 */

public class Login extends AppCompatActivity{
    private EditText username=null;
    private EditText password=null;
    LoginDataBaseAdapter loginDataBaseAdapter;
    Button login;
    TextView signUp;
    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Get The Refference Of Buttons
        login = (Button) findViewById(R.id.loginbutton);
        signUp = (TextView) findViewById(R.id.register_login);
        // Set OnClick Listener on SignUp button
        signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent intentSignUP = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intentSignUP);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }

    public void login(View view){
        username = (EditText)findViewById(R.id.user_loginText);
        password = (EditText)findViewById(R.id.user_passwordText);
        String userName=username.getText().toString();
        String passWord=password.getText().toString();
        String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

        if(passWord.equals(storedPassword)){
            loginDataBaseAdapter.insertUserEntry(userName, storedPassword);
            Toast.makeText(getApplicationContext(), "Redirecting...",
                    Toast.LENGTH_SHORT).show();//intent code block
            Intent lIntent = new Intent();
            lIntent.setClass(this, MainActivity.class);
            startActivity(lIntent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();

        }
    }


    }

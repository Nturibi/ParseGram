package com.knturibifb.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    private EditText userNameInput;
    private EditText passwordInput;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null){
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.main_fragment_holder, new LoginFragment());
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Complete the changes added above
            ft.commit();
        }else{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }


    }



}

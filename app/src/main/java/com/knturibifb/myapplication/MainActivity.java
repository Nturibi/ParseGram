package com.knturibifb.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText userNameInput;
    private EditText passwordInput;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_fragment_holder, new LoginFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

//        userNameInput = (EditText) findViewById(R.id.etUsername);
//        passwordInput = (EditText) findViewById(R.id.etPassword);
//        loginBtn = (Button) findViewById(R.id.btLogin);
//
//        userNameInput.setText("knturibi");
//        passwordInput.setText("password123");
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final String username = userNameInput.getText().toString();
//                final String password = passwordInput.getText().toString();
//                login(username, password);
//            }
//        });
    }

//    private void login (String username, String password){
//        //TODO
//        ParseUser.logInInBackground(username, password, new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if (e == null){   //means that user was logged in correctly
//                    Log.d("LoginActivity", "Login successful");
//                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else{
//                    Log.d("LoginActivity", "Login Failure");
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

}

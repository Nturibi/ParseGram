package com.knturibifb.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginFragment extends Fragment {
    private EditText userNameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private TextView signupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_login, parent, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View mainView = getView();
        userNameInput = (EditText) mainView.findViewById(R.id.etUsername);
        passwordInput = (EditText) mainView.findViewById(R.id.etPassword);
        loginBtn = (Button) mainView.findViewById(R.id.btLogin);
        signupButton = (TextView) mainView.findViewById(R.id.tvSignup);

        userNameInput.setText("knturibi");
        passwordInput.setText("password123");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = userNameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_holder, new SignupFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void login (String username, String password){
        //TODO
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){   //means that user was logged in correctly
//                    Log.d("LoginActivity", "Login successful");
                    final Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    Log.d("LoginActivity", "Login Failure");
                    e.printStackTrace();
                }

            }
        });
    }




}

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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupFragment extends Fragment {
    private Button signup;
    private EditText email;
    private EditText username;
    private EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_signup, parent, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View mainView = getView();
        signup = (Button) mainView.findViewById(R.id.btSignup);
        email = (EditText) mainView.findViewById(R.id.etSignupEmail);
        username = (EditText) mainView.findViewById(R.id.etSignupUsername);
        password = (EditText) mainView.findViewById(R.id.etSignupPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the ParseUser
                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(email.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("Signupfragment", "successfully registered");
                            //need to launch the posts page
                            final Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Log.d("Signupfragment", "Error registering");
                        }
                    }
                });

            }
        });


    }
}

package com.knturibifb.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    private Button logoutButton;
    private TextView username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View mainView = getView();
        logoutButton = mainView.findViewById(R.id.btLogout);
        username = mainView.findViewById(R.id.tvProfUsername);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                //need to launch the main page
                final Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ParseUser user = ParseUser.getCurrentUser();
        username.setText(user.getUsername());

    }
}

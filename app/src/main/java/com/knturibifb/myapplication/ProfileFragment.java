package com.knturibifb.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class ProfileFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button logoutButton;
    private TextView username;
    private TextView email;
    private Button uploadButton;
    private ParseImageView profileHolder;
    private static File filesDir;
    private ParseFile parseFile;
    private File file;
    private Button updateButton;
    private EditText aboutMessage;
    private TextView  aboutHolder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        filesDir = getContext().getFilesDir();
        View mainView = getView();
        logoutButton = mainView.findViewById(R.id.btLogout);
        username = mainView.findViewById(R.id.tvProfUsername);
        email = mainView.findViewById(R.id.tvUserEmail);
        uploadButton = mainView.findViewById(R.id.ivNewPic);
        profileHolder = mainView.findViewById(R.id.ivProfileImg);
        updateButton = mainView.findViewById(R.id.btUpdateMes);
        aboutHolder = mainView.findViewById(R.id.tvAbout);
        aboutMessage = mainView.findViewById(R.id.etUpdateMes);


        final ParseUser user = ParseUser.getCurrentUser();
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
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String message = aboutMessage.getText().toString();
                user.put("about", message);
                user.saveInBackground();
                aboutHolder.setText(message);
            }
        });

        username.setText(user.getUsername());
        email.setText(user.getEmail());
        if (user.get("about")!= null) aboutHolder.setText(user.get("about").toString());
        profileHolder.setParseFile(user.getParseFile("picture"));
        uploadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isStoragePermissionGranted())   dispatchTakePictureIntent();
            }
        });
        profileHolder.loadInBackground();

    }

    /*Requests permission to read external storage*/
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Profile Fragment","Permission is granted");
                return true;
            } else {

                Log.v("Profile Fragment","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Profile Fragment","Permission is granted");
            return true;
        }
    }

    /*dispatches an intent to take a picture*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Profile Fragment", "Called activity ion prof frag");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileHolder.setImageBitmap(imageBitmap);
            file = persistImage(imageBitmap, "pic1");
            if(isStoragePermissionGranted()){
                parseFile = new ParseFile(file);
                System.out.println("GOTTEN THE PARSE FILE");

                ParseUser parseUser = ParseUser.getCurrentUser();
                parseUser.put("picture", parseFile);
                parseUser.saveInBackground();
                Toast.makeText(getActivity(),"Updated profile",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private static File persistImage(Bitmap bitmap, String name) {
        File imageFile = new File(filesDir, name + ".jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.d("ProfileFragment", "Error writing bitmap", e);
        }
        return imageFile;

    }
}

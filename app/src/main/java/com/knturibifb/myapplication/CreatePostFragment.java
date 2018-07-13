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
import android.widget.ImageView;
import android.widget.Toast;

import com.knturibifb.myapplication.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class CreatePostFragment extends Fragment {
    private static  final String imagePath =   "/storage/emulated/0/DCIM/Camera/pic.jpg";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static File filesDir;

    private EditText descriptionInput;
    private Button createButton;
    private Button cameraButton;
    private ImageView pictureHolder;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private ParseFile parseFile;
    private File file;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_create_post, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("Create Post Fragment", "At create post");
        // Setup any handles to view objects
        filesDir = getContext().getFilesDir();

        View mainView = getView();

        descriptionInput = (EditText)  mainView.findViewById(R.id.etDescription);
        createButton = (Button) mainView.findViewById(R.id.btCreate);
        pictureHolder = (ImageView) mainView.findViewById(R.id.ivPicture);
        cameraButton = (Button) mainView.findViewById(R.id.btCamera);




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                if (file == null) {
                    System.out.println("file is null");
                    Toast.makeText(getContext(), "An image, please!", Toast.LENGTH_SHORT).show();
                    return;
                    //file = new File(imagePath);
                }
                if(isStoragePermissionGranted()){

                  parseFile = new ParseFile(file);
                  createPost(description, parseFile, user);
                }

            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isStoragePermissionGranted())   dispatchTakePictureIntent();
            }

        });



    }
    private void createPost(String description, ParseFile imageFile, ParseUser user){
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(getContext(), "Posted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    /*Requests permission to read external storage*/
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Create Post Fragment","Permission is granted");
                return true;
            } else {

                Log.v("Create Post Fragment","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Create Post Fragment","Permission is granted");
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
        Log.d("CreatePostFragment", "Called act res in frag");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pictureHolder.setImageBitmap(imageBitmap);
            file = persistImage(imageBitmap, "pic1");
            System.out.println("finished activity result");

//            if(isStoragePermissionGranted()){
////                parseFile = new ParseFile(file);
////                createPost("wooow", parseFile,ParseUser.getCurrentUser() );
////            }

        }
    }
    private static File  persistImage(Bitmap bitmap, String name) {
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.d("HomeActivity", "Error writing bitmap", e);
        }
        return imageFile;

    }

}

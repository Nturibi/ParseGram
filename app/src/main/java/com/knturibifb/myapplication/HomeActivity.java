package com.knturibifb.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.knturibifb.myapplication.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static  final String imagePath =   "/storage/emulated/0/DCIM/Camera/pic.jpg";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static File filesDir;

    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        filesDir = getApplicationContext().getFilesDir();

        descriptionInput = (EditText) findViewById(R.id.etDescription);
        createButton = (Button) findViewById(R.id.btCreate);
        refreshButton = (Button) findViewById(R.id.btRefresh);
        pictureHolder = (ImageView) findViewById(R.id.ivPicture);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                if (file == null) {
                    System.out.println("file is null");
                    file = new File(imagePath);
                }
                if(isStoragePermissionGranted()){

                  parseFile = new ParseFile(file);
                  createPost(description, parseFile, user);
                }

            }
        });



        loadTopPosts();

    }

    private void createPost(String description, ParseFile imageFile, ParseUser user){
        //TODO
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.d("HomeActivity", "Create post success");

                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadTopPosts(){
        //get the top posts
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for (int i = 0; i < objects.size(); i++){
                        Log.d("HomeActivity", "Post["+i+"] ="+objects.get(i).getDescription()+"\n username = "+objects.get(i).getUser().getUsername());
                    }

                }else{
                    e.printStackTrace();
                }
            }
        });


    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /*Requests permission to read external storage*/
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("HOMEACTIVITY","Permission is granted");
                return true;
            } else {

                Log.v("HOMEACTIVITY","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("HOMEACTIVITY","Permission is granted");
            return true;
        }
    }

    /*Executed when the camera button is clicked*/
    public void onClickCamera(View view){
        dispatchTakePictureIntent();
    }

    /*dispatches an intent to take a picture*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pictureHolder.setImageBitmap(imageBitmap);
            file = persistImage(imageBitmap, "pic1");
            System.out.println("finished activity result");

            if(isStoragePermissionGranted()){
                parseFile = new ParseFile(file);
                createPost("wooow", parseFile,ParseUser.getCurrentUser() );
            }

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

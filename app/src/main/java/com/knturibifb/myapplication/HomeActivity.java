package com.knturibifb.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.knturibifb.myapplication.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;

    private static  final String imagePath =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/document/19FB-081A:Camera/cat.jpg";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        descriptionInput = (EditText) findViewById(R.id.etDescription);
        createButton = (Button) findViewById(R.id.btCreate);
        refreshButton = (Button) findViewById(R.id.btRefresh);

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
                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);
                createPost(description, parseFile, user);
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

}

package com.knturibifb.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.knturibifb.myapplication.model.Post;
import com.parse.ParseImageView;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {
    private Post post;
    private TextView username;
    private TextView description;
    private TextView time;
    private TextView numLikes;
    private ParseImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        Log.d("DetailActivity", "Inside here");
        username  = (TextView) findViewById(R.id.tvUsername2);
        username.setText(post.getUser().getUsername());
        description = (TextView) findViewById(R.id.tvDescription);
        description.setText(post.getDescription());
        time = (TextView)findViewById(R.id.tvCreatedAt);
        time.setText(post.getCreatedAt().toString());
        img = (ParseImageView) findViewById(R.id.ivDetailPic);
        img.setParseFile(post.getImage());
        img.loadInBackground();
    }
}

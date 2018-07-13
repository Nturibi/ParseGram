package com.knturibifb.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.knturibifb.myapplication.model.Post;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        Log.d("DetailActivity", "Inside here");
    }
}

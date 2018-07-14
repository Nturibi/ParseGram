package com.knturibifb.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.knturibifb.myapplication.model.Post;
import com.parse.ParseImageView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private Post post;
    private TextView description;
    private TextView time;
    private TextView numLikes;
    private ParseImageView img;
    private TextView username1;
    private ParseImageView profImg;
    private String numberLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        username1 = (TextView) findViewById(R.id.tvUserName);
        username1.setText(post.getUser().getUsername());

        description = (TextView) findViewById(R.id.tvDescription);
        description.setText(post.getDescription());

        time = (TextView)findViewById(R.id.tvCreatedAt);
        time.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));

        numLikes = (TextView)findViewById(R.id.tvNumLikes);
        numLikes.setText(post.getNumLikes() +" likes");
        numberLikes = post.getNumLikes();

        img = (ParseImageView) findViewById(R.id.ivDetailPic);
        img.setParseFile(post.getImage());

        profImg = (ParseImageView) findViewById(R.id.ivProfPic);
        profImg.setParseFile(post.getUser().getParseFile("picture"));

        img.loadInBackground();
        profImg.loadInBackground();
    }
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        }catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public void onClickLike(View view){
        Log.d("Details: ", "Clicked like");
        post.likePost();
        post.saveInBackground();
        int previousVal = Integer.parseInt(numberLikes);
        numLikes.setText(Integer.toString(previousVal + 1) +" likes");
    }
}

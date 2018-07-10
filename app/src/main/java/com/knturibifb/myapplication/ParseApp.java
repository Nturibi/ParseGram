package com.knturibifb.myapplication;

import android.app.Application;

import com.knturibifb.myapplication.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {


    @Override
    public void onCreate() {
        ParseObject.registerSubclass(Post.class);
        super.onCreate();
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("knturibi-parsegram")
                .clientKey("menasf")
                .server("http://knturibi-parsegram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}

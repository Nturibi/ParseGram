package com.knturibifb.myapplication.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


@ParseClassName("Post")
public class Post extends ParseObject {
    private static  final String KEY_DESCRIPTION = "description";
    private static  final String KEY_IMAGE = "image";
    private static  final String KEY_USER = "user";
    private static  final String KEY_LIKES = "likes";
//    private static  final String KEY_TIME = "time";


    public  String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser parseUser){
        put(KEY_USER, parseUser);

    }


    public void likePost(){
        int numLikes = 0;
        if (getString("likes") != null) numLikes = Integer.parseInt(getString("likes"));
        numLikes++;
        put(KEY_LIKES,Integer.toString(numLikes));
    }

    public  String getNumLikes(){
        if (getString("likes") == null) return "0";
        return getString("likes");
    }

    public static class Query extends ParseQuery<Post>{
        public Query() {
            super(Post.class);
        }

        public Query getTop(){
            setLimit(40);
            return this;
        }

        public Query withUser (){
            include("user");
            return this;
        }
    }


}

package com.knturibifb.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.knturibifb.myapplication.model.Post;
import com.parse.ParseImageView;

import java.util.List;

public class PostAdapter extends
        RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;

    public PostAdapter(List<Post> posts){
        mPosts = posts;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView numLikes;
        public ImageView directMessage;
        public ParseImageView postPic;
        public ImageView comment;
        public TextView description;
        public ImageView like;
        public TextView userName;
        public ParseImageView userPic;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            numLikes = (TextView) itemView.findViewById(R.id.tvNumLikes);
            directMessage = (ImageView) itemView.findViewById(R.id.ivDirectMessage);
            postPic = (ParseImageView) itemView.findViewById(R.id.ivPostPic);
            comment = (ImageView) itemView.findViewById(R.id.ivComment);
            description = (TextView) itemView.findViewById(R.id.tvDescription);
            like = (ImageView) itemView.findViewById(R.id.ivLike);
            userName = (TextView) itemView.findViewById(R.id.tvUserName);
            userPic = (ParseImageView) itemView.findViewById(R.id.ivProfPic);

        }
    }


    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.item_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Post post = mPosts.get(position);

        // Set item views based on your views and data model
        viewHolder.description.setText(post.getDescription());
        viewHolder.userName.setText(post.getUser().getUsername());
        viewHolder.postPic.setParseFile(post.getImage());
        //viewHolder.userPic.setParseFile(post.getUser().g);
        viewHolder.postPic.loadInBackground();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPosts.size();
    }




}


package com.knturibifb.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knturibifb.myapplication.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ArrayList<Post> posts;
    private RecyclerView rvPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    // This event is triggered soon after onCreateView().
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        posts = new ArrayList<>();
        View mainView = getView();
        //get the recyclerview
         rvPosts = (RecyclerView) mainView.findViewById(R.id.rvPosts);
        //update posts with the latest posts
        loadTopPosts();

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
                        Log.d("HomeFragment", "Post["+i+"] ="+objects.get(i).getDescription()+"\n username = "+objects.get(i).getUser().getUsername());
                        posts.add(objects.get(i));
                    }
                    //create a posts adapter
                    PostAdapter adapter = new PostAdapter(posts);
                    //attach the adapter to the recyclerview to populate items
                    rvPosts.setAdapter(adapter);
                    //set the layout manager to position the items
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    mLayoutManager.setReverseLayout(true);
                    mLayoutManager.setStackFromEnd(true);
                    rvPosts.setLayoutManager(mLayoutManager);

                }else{
                    e.printStackTrace();
                }
            }
        });
    }


}

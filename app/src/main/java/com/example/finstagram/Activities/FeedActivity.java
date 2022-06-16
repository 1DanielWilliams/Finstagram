package com.example.finstagram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.finstagram.Models.Post;
import com.example.finstagram.PostAdapter;
import com.example.finstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView rvPost;
    protected List<Post> allPosts;
    protected PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        rvPost = findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        adapter = new PostAdapter(this, allPosts);

        rvPost.setAdapter(adapter);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        queryPosts();
    }

    private void queryPosts() {
        // Specify the type of data that will be queried
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Include data referred by user key
        query.include(Post.KEY_USER);
        // Limits query to latest 20 items
        query.setLimit((20));
        // orders posts by creation date (newest first)
        query.addDescendingOrder("createdAt");

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {

                if (e != null) {
                    Log.e("FeedActivity", "issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i("FeedActivity", "Post: " + post.getDescription() + " , Username: " + post.getUser().getUsername());
                }

                // Saves the received posts to list and notify the adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
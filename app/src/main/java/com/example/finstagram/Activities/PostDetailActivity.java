package com.example.finstagram.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finstagram.Models.Post;
import com.example.finstagram.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class PostDetailActivity extends AppCompatActivity {

    private ImageView ivPostDetail;
    private TextView tvUsernameDetail;
    private TextView tvDescriptionDetail;
    private TextView tvTimestampDetail;
    private TextView tvNumLikesDetail;
    private TextView tvLikeDetails;
    private ImageView ivProfileImageDetail;
    private TextView tvUsernameDetailSmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ivPostDetail = findViewById(R.id.ivPostDetail);
        tvUsernameDetail = findViewById(R.id.tvUsernameDetail);
        tvDescriptionDetail = findViewById(R.id.tvDescriptionDetail);
        tvTimestampDetail = findViewById(R.id.tvTimestampDetail);
        tvNumLikesDetail = findViewById(R.id.tvNumLikesDetail);
        tvLikeDetails = findViewById(R.id.tvLikeDetails);
        ivProfileImageDetail = findViewById(R.id.ivProfileImageDetail);
        tvUsernameDetailSmall = findViewById(R.id.tvUsernameDetailSmall);


        if (getIntent().getExtras() != null) {
            Post post = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());
            tvUsernameDetail.setText(post.getUser().getUsername());
            tvDescriptionDetail.setText(post.getDescription());
            tvTimestampDetail.setText( Post.getRelativeTimeAgo(post.getCreatedAt()));
            Glide.with(this).load(post.getImage().getUrl()).into(ivPostDetail);
            int numLikes = post.getLikes();
            tvNumLikesDetail.setText(String.valueOf(numLikes));
            if (numLikes > 1) {
                tvLikeDetails.setText("Likes");
            } else {
                tvLikeDetails.setText("Like");
            }

            tvUsernameDetailSmall.setText(post.getUser().getUsername());
            ParseFile profileImage = post.getUser().getParseFile("profileImage");
            if (profileImage != null) {
                Glide.with(this).load(profileImage.getUrl()).circleCrop().into(ivProfileImageDetail);
            } else {
                ivProfileImageDetail.setImageResource(R.drawable.default_profile);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                ParseUser.logOut();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                return true;
            case R.id.addProfilePic:
                i = new Intent(this, ProfilePictureActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
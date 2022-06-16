package com.example.finstagram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finstagram.Models.Post;
import com.example.finstagram.R;

public class PostDetailActivity extends AppCompatActivity {

    private ImageView ivPostDetail;
    private TextView tvUsernameDetail;
    private TextView tvDescriptionDetail;
    private TextView tvTimestampDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ivPostDetail = findViewById(R.id.ivPostDetail);
        tvUsernameDetail = findViewById(R.id.tvUsernameDetail);
        tvDescriptionDetail = findViewById(R.id.tvDescriptionDetail);
        tvTimestampDetail = findViewById(R.id.tvTimestampDetail);

        if (getIntent().getExtras() != null) {
            Post post = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());
            Log.i("PostDetailActivity", "onCreate: " + post.getUser().getUsername());
            tvUsernameDetail.setText(post.getUser().getUsername());
            tvDescriptionDetail.setText(post.getDescription());
            tvTimestampDetail.setText( Post.getRelativeTimeAgo(post.getCreatedAt()));
            Glide.with(this).load(post.getImage().getUrl()).into(ivPostDetail);
        }

    }
}
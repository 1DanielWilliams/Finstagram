package com.example.finstagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finstagram.Activities.FeedActivity;
import com.example.finstagram.Activities.PostDetailActivity;
import com.example.finstagram.Activities.SignUpActivity;
import com.example.finstagram.Models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;


    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        post.position = position;
        holder.rootView.setTag(post);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        private TextView tvPostName;
        private ImageView ivPost;
        private TextView tvDescription;
        private TextView tvUsernameSmall;
        private TextView tvRelativetime;
        private ImageButton btnLikeFeed;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            tvPostName = itemView.findViewById(R.id.tvPostName);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvRelativetime = itemView.findViewById(R.id.tvRelativetime);
            tvUsernameSmall = itemView.findViewById(R.id.tvUsernameSmall);
            btnLikeFeed = itemView.findViewById(R.id.btnLikeFeed);

            btnLikeFeed.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = posts.get(position);
                    if (post.getIsLiked()) {
                        //unlike the post
                        post.setLikes(post.getLikes() - 1);
                        post.setIsLiked(false);

                    } else{
                        //like the post
                        post.setIsLiked(true);

                        post.setLikes(post.getLikes() + 1);
                    }

                    //update the post in the background?
                    try {
                        post.save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // update the adapter
                    notifyDataSetChanged();
                }
            });

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Post post = posts.get(position);
                        Intent i = new Intent(context, PostDetailActivity.class);
                        i.putExtra(Post.class.getSimpleName(), post);
                        context.startActivity(i);
                    }
                }
            });
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvPostName.setText(post.getUser().getUsername());
            tvUsernameSmall.setText(post.getUser().getUsername());
            tvRelativetime.setText(Post.getRelativeTimeAgo(post.getCreatedAt()));

            if (posts.get(post.position).getIsLiked()) {
                btnLikeFeed.setColorFilter(Color.argb(255, 255, 0, 0));
            } else {
                btnLikeFeed.setColorFilter(Color.argb(0, 255, 0, 0));

            }

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load((image.getUrl())).into(ivPost);

            }
        }
    }

}

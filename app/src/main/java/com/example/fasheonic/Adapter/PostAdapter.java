package com.example.fasheonic.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fasheonic.Model.Post;
import com.example.fasheonic.Model.User;
import com.example.fasheonic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public ArrayList<Post> mPost;
    Post post;


    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, ArrayList<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.post_item,viewGroup,false);
        return new PostAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        post=mPost.get(i);

        /////////Zee edited bcoz Glide not working for new Image Loader refer this : (https://square.github.io/picasso/#download)
       // Glide.with(mContext).load(post.getPostimage()).into(viewHolder.post_image);
        String z = "https://firebasestorage.googleapis.com/v0/b/fasheonic-11012z.appspot.com/o/posts%2F1571450079341.null?alt=media&token=8e138ba5-d4ae-406f-a011-cc4c61d86999";

         Glide.with(mContext).load(mPost.get(i).getPostimage()).into(viewHolder.post_image);
        //Picasso.get().load(z).placeholder(R.drawable.ic_account_circle_black_24dp).into(viewHolder.post_image);
        //Picasso.get().load(mPost.get(i).getPostimage()).placeholder(R.drawable.ic_pic).into(viewHolder.post_image);
        if(post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);
        }
        else{
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(post.getDescription());
        }

        publisherInfo(viewHolder.image_profile,viewHolder.username,viewHolder.publisher,post.getPublisher());
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image_profile,post_image,like,comment,save;
        public TextView username,likes,publisher,description,comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.image_profileA);
            post_image = itemView.findViewById(R.id.post_imageA);
            like = itemView.findViewById(R.id.likeA);
            comment = itemView.findViewById(R.id.commentA);
            save = itemView.findViewById(R.id.saveA);
            username = itemView.findViewById(R.id.usernameAS);
            likes = itemView.findViewById(R.id.LikesA);
            publisher = itemView.findViewById(R.id.publisherA);
            description = itemView.findViewById(R.id.descriptionA);
            comments = itemView.findViewById(R.id.commentsA);
        }
    }

    private  void publisherInfo(final ImageView image_profile, final TextView username, final TextView publisher, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                publisher.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

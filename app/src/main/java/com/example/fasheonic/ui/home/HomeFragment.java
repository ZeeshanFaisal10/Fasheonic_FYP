package com.example.fasheonic.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fasheonic.Adapter.PostAdapter;
import com.example.fasheonic.Model.Post;
import com.example.fasheonic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postList;
    FirebaseAuth auth;
    DatabaseReference reference;
    Context mContext;
    private  List<String> followingList;


    ProgressBar circular_progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        reference = FirebaseDatabase.getInstance().getReference("posts");
        recyclerView=view.findViewById(R.id.recycler_viewA);
       circular_progressBar = view.findViewById(R.id.progress_circle);
        auth = FirebaseAuth.getInstance();
        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //postList=new ArrayList<>();

       // recyclerView.setAdapter(postAdapter);
        //checkFollowing();
        //new AsyncCaller().execute();
        clearAll();
        //isWorking();
        checkFollowing();
        return view;
    }



    private void checkFollowing(){
        followingList=new ArrayList<>();
        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference("Follow")
                .child(auth.getCurrentUser().getUid()).child("following");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    followingList.add(snapshot.getKey());
                }
                new AsyncCaller().execute();

                //readPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }








    private void readPosts(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    for (String id : followingList) {

                        if (post.getPublisher().equals(id)) {

                            postList.add(post);
                        }
                    }
                }
                postAdapter.notifyDataSetChanged();
                circular_progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private class AsyncCaller extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            Query query = reference;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Post posts = new Post();
                        posts.setPostimage(snapshot.child("postImage").getValue().toString());
                        posts.setDescription(snapshot.child("description").getValue().toString());
                        posts.setPostid(snapshot.child("postid").getValue().toString());
                        posts.setPublisher(snapshot.child("publisher").getValue().toString());
                        for (String id : followingList) {

                            if (posts.getPublisher().equals(id)) {

                                postList.add(posts);
                            }
                        }
                    }
                    postAdapter=new PostAdapter(getContext(),postList);
                    recyclerView.setAdapter(postAdapter);
                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            circular_progressBar.setVisibility(View.INVISIBLE);
        }
    }


    public void isWorking(){

    }

    private void clearAll(){
       if (postList != null) {
           postList.clear();
       if(postAdapter != null){
           postAdapter.notifyDataSetChanged();
       }
       }
       postList = new ArrayList<>();
    }

}
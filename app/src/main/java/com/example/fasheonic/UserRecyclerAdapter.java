package com.example.fasheonic;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fasheonic.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    private List<User> mUsers;
    private List<User> mUsersListAll;
    private Context mContext;
    private FirebaseUser firebaseUser;

    public UserRecyclerAdapter(Context mContext,List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        mUsersListAll = new ArrayList<>();
        mUsersListAll.addAll(mUsers);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.show_itemview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
        // View view= LayoutInflater.from(mContext).inflate(R.layout.show_itemview,viewGroup,false);
        //  return new UserRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final User user=mUsers.get(position);


        viewHolder.btn_follow.setVisibility(View.VISIBLE);
        viewHolder.username.setText(user.getUsername());
        viewHolder.fullname.setText(user.getFullname());
        //Glide.with(mContext).load(user.getImageurl()).into(viewHolder.image_profile);
        viewHolder.image_profile.setImageResource(R.drawable.ic_account_circle_black_24dp);
        isFollowing(user.getId(),viewHolder.btn_follow);
        // viewHolder.rowCountTextView.setText(String.valueOf(position));
        // viewHolder.textView.setText(mUsers.get(position));

        ///////////Final Linking to Firebase
        if (user.getId().equals(firebaseUser.getUid())){
            viewHolder.btn_follow.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",user.getId());
                editor.apply();

                // ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                //          new SearchFragment()).commit();


                if(viewHolder.btn_follow.getText().toString().equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following")
                            .child(user.getId()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId()).child("followers")
                            .child(firebaseUser.getUid()).setValue(true);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following")
                            .child(user.getId()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId()).child("followers")
                            .child(firebaseUser.getUid()).removeValue();
                }


            }
        });

    }

    @Override
    public int getItemCount() {

        return mUsers.size();
    }

//    @Override
//    public Filter getFilter() {
//
//        return myFilter;
//    }

//    private Filter myFilter = new Filter() {
//
//        //Automatic on background thread
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//
//
//            List<User> filteredList = new ArrayList<>();
//
//            if (charSequence == null || charSequence.length() == 0) {
//                filteredList.addAll(mUsersListAll);
//            } else {
//                for (User u: mUsersListAll) {
//                    if (u.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
//                        filteredList.add(u);
//                    }
//                }
//            }
//
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredList;
//            return filterResults;
//        }
//
//        //Automatic on UI thread
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            mUsers.clear();
//            mUsers.addAll((Collection<? extends User>) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };



    public class ViewHolder extends RecyclerView.ViewHolder{

        //ImageView imageView;
        //TextView textView, rowCountTextView;
        TextView username;
        TextView fullname;
        ImageView image_profile;
        Button btn_follow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // imageView = itemView.findViewById(R.id.imageView);
            // textView = itemView.findViewById(R.id.textView3);
            //  rowCountTextView = itemView.findViewById(R.id.textView4);
            username=itemView.findViewById(R.id.username1);
            fullname=itemView.findViewById(R.id.fullname1);
            image_profile=itemView.findViewById(R.id.image_profile1);
            btn_follow=itemView.findViewById(R.id.btn_follow1);

        }

    }
    private  void isFollowing(final String userid, final Button button){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(userid).exists()){
                    button.setText("following");
                } else{
                    button.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}


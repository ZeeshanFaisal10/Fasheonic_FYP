package com.example.fasheonic.ui.Logout;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fasheonic.LogSign;
import com.example.fasheonic.R;
import com.google.firebase.auth.FirebaseAuth;


public class LogoutFragment extends Fragment {


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_logout, container, false);
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), LogSign.class));

        // Inflate the layout for this fragment
        return view;


    }

}

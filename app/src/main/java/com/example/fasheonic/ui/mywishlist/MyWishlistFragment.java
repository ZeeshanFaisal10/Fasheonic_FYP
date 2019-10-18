package com.example.fasheonic.ui.mywishlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fasheonic.R;

public class MyWishlistFragment extends Fragment {

    private MyWishlistViewModel myWishlistViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myWishlistViewModel =
                ViewModelProviders.of(this).get(MyWishlistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mywishlist, container, false);
        //  final TextView textView = root.findViewById(R.id.text_tools);
        myWishlistViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //    textView.setText(s);
            }
        });
        return root;
    }
}
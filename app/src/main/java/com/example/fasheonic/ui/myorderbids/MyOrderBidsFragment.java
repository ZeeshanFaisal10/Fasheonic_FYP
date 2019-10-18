package com.example.fasheonic.ui.myorderbids;

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

public class MyOrderBidsFragment extends Fragment {

    private MyOrderBidsViewModel myOrderBidsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myOrderBidsViewModel =
                ViewModelProviders.of(this).get(MyOrderBidsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myorderbids, container, false);
        // final TextView textView = root.findViewById(R.id.text_slideshow);
        myOrderBidsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //    textView.setText(s);
            }
        });
        return root;
    }

}
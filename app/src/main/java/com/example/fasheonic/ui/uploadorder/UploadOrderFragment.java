package com.example.fasheonic.ui.uploadorder;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fasheonic.R;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class UploadOrderFragment extends Fragment {

    private UploadOrderViewModel uploadOrderViewModel;
    


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //uploadOrderViewModel =
          //      ViewModelProviders.of(this).get(UploadOrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_upload_order, container, false);
        //final TextView textView = root.findViewById(R.id.uploadordertext);
        //uploadOrderViewModel.getText().observe(this, new Observer<String>() {
          //  @Override
           // public void onChanged(@Nullable String s) {
             //   textView.setText(s);
            //}
        //});
        //textView = root.findViewById(R.id.uploadordertext);

        //getActivity().setTitle(R.id.uploadordertext);
        return root;
    }

}
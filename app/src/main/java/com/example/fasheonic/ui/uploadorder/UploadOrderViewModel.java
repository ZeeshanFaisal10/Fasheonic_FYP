package com.example.fasheonic.ui.uploadorder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UploadOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UploadOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is upload order fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
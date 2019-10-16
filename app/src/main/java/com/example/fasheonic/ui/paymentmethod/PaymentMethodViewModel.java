package com.example.fasheonic.ui.paymentmethod;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentMethodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PaymentMethodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is payment method fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
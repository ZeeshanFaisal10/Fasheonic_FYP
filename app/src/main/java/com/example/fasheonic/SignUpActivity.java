package com.example.fasheonic;


import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register;
    private EditText email;
    private EditText password;
    private TextView logintext;
    EditText username,fullname1;
    ProgressDialog pd;
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(SignUpActivity.this);
        btn_register = findViewById(R.id.register);
        username=findViewById(R.id.username);
        fullname1=findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        logintext = findViewById(R.id.logintext);

        btn_register.setOnClickListener(this);
        logintext.setOnClickListener(this);
    }
    private void RegisterUser() {
        String str_username = username.getText().toString();
        String str_fullname = fullname1.getText().toString();
        final String em = email.getText().toString();
        String pass = password.getText().toString();
        if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) ||
                TextUtils.isEmpty(em) || TextUtils.isEmpty(pass)) {
            Toast.makeText(SignUpActivity.this, "All filled are required", Toast.LENGTH_SHORT).show();
            return;
        }

        pd.setMessage("Please Wait..");
        pd.show();
        //Send email to new users

        firebaseAuth.createUserWithEmailAndPassword(em, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(!user.isEmailVerified()){
                                user.sendEmailVerification();
                                Toast.makeText(SignUpActivity.this, "Registeration mail sent, check your email!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                            saveUserProfileData();

                        } else {

                                Toast.makeText(SignUpActivity.this, "Authentication failed. Please Try again!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                return;
                        }



                    }
                });

        }

public void saveUserProfileData(){
        String f_name = fullname1.getText().toString();
        String u_name = username.getText().toString();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    String userid=firebaseUser.getUid();
    reference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
    HashMap<String,Object> hashMap=new HashMap<>();
    hashMap.put("id",userid);
    hashMap.put("username",u_name);
    hashMap.put("fullname",f_name);
    hashMap.put("bio","");
    hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/fasheonic-895ca.appspot.com/o/logo.png?alt=media&token=1d892c1a-b417-4e36-b30c-de94c76b7f45");
    //Send Data to Firebase Database
    reference.setValue(hashMap);
    //Open Login Activity
    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_register){
        if(isNetworkAvailable()){
            RegisterUser();
        }else {
            Toast.makeText(this,"No internet connection!",Toast.LENGTH_SHORT).show();

        }

        }
        if(view == logintext){
            goBack();
        }
    }

    private void goBack() {
        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;

        }
    }
}

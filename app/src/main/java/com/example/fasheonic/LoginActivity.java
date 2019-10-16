package com.example.fasheonic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_login;
    TextView forget_pass;
    EditText user_email;
    EditText user_pass;
    ProgressDialog pd;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        forget_pass = findViewById(R.id.forgotpass_u);
        btn_login = findViewById(R.id.login_btn);
        user_email = findViewById(R.id.email_u);
        user_pass = findViewById(R.id.password1);
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(this);
        forget_pass.setOnClickListener(this);

    }
    public void LoginUser() {
        pd=new ProgressDialog(LoginActivity.this);
        pd.setMessage("Please Wait...");
        pd.show();
        String str_email = user_email.getText().toString();
        String str_password = user_pass.getText().toString();
        if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
            Toast.makeText(this, "All filled are required", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }else {
         /////Database Login
            signInUser(str_email,str_password);
        }



    }

    public void signInUser(String x, String y){
        mAuth.signInWithEmailAndPassword(x, y)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "welcome.",
                                    Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();


                            }else {
                                Toast.makeText(LoginActivity.this, "Please Verify your email first!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            pd.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed: Incorrect username or password!",
                                    Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == btn_login){
            if(isNetworkAvailable()){
                LoginUser();
            }else{

                Toast.makeText(LoginActivity.this,"no internet connection!",Toast.LENGTH_SHORT).show();
            }

        }
        if(view == forget_pass){
            Toast.makeText(this,"Check your mail for Recovery link!",Toast.LENGTH_SHORT).show();
        }
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

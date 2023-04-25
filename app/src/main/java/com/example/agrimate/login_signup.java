package com.example.agrimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class login_signup extends AppCompatActivity {

    public static String PREF_NAME = "MyPrefFile";

    EditText loginUserEmail, loginUserPassword;
    Button login_btn;

    public static String PREFS_NAME = "MyPrefsFile";

    EditText signUpemail, signUpPhone, signUpUserPassword, signUpUser_confPassword;
    Button signup_btn;

    FirebaseAuth mAuth;
    FirebaseAuth mAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        mAuth = FirebaseAuth.getInstance();
        mAuth1 = FirebaseAuth.getInstance();

        loginUserEmail = findViewById(R.id.email);
        loginUserPassword = findViewById(R.id.loginUserPassword);
        login_btn = findViewById(R.id.login_btn);

        signUpemail = findViewById(R.id.signUpemail);
        signUpPhone = findViewById(R.id.signUpPhone);
        signUpUserPassword = findViewById(R.id.signUpUserPassword);
        signUpUser_confPassword = findViewById(R.id.signUpUser_confPassword);
        signup_btn = findViewById(R.id.signup_btn);
    }
}
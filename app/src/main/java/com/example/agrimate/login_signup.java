package com.example.agrimate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class login_signup extends AppCompatActivity {

    public static String PREF_NAME = "MyPrefFile";

    LinearLayout sigunp_layout, login_layout;
    EditText loginUserEmail, loginUserPassword;
    Button login_btn;

    public static String PREFS_NAME = "MyPrefsFile";

    EditText signUpName, signUpemail, signUpPhone, signUpUserPassword;
    Button signup_btn;
    TextView alreadyHave_acc, loginTxt;
    TextView dont_have_acc, register_acc;

    FirebaseAuth mAuth;
    FirebaseAuth mAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mAuth1 = FirebaseAuth.getInstance();

        sigunp_layout = findViewById(R.id.sigunp_layout);
        login_layout = findViewById(R.id.login_layout);

        loginUserEmail = findViewById(R.id.email);
        loginUserPassword = findViewById(R.id.loginUserPassword);
        login_btn = findViewById(R.id.login_btn);

        signUpName = findViewById(R.id.signUpName);
        signUpemail = findViewById(R.id.signUpemail);
        signUpPhone = findViewById(R.id.signUpPhone);
        signUpUserPassword = findViewById(R.id.signUpUserPassword);
        signup_btn = findViewById(R.id.signup_btn);

        alreadyHave_acc = findViewById(R.id.alreadyHave_acc);
        loginTxt = findViewById(R.id.login_acc);

        dont_have_acc = findViewById(R.id.dont_have_acc);
        register_acc = findViewById(R.id.register_acc);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(login_signup.PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasRegistered",true);
                editor.apply();
                registerUser();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(login_signup.PREF_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasLoggedIn",true);
                editor.apply();
                loginUser();

            }
        });

        alreadyHave_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigunp_layout.setVisibility(View.GONE);
                login_layout.setVisibility(View.VISIBLE);
                alreadyHave_acc.setVisibility(View.GONE);
                dont_have_acc.setVisibility(View.VISIBLE);
                register_acc.setVisibility(View.VISIBLE);
                loginTxt.setVisibility(View.GONE);
            }
        });

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigunp_layout.setVisibility(View.GONE);
                login_layout.setVisibility(View.VISIBLE);
                alreadyHave_acc.setVisibility(View.VISIBLE);
                dont_have_acc.setVisibility(View.GONE);
                register_acc.setVisibility(View.GONE);
                loginTxt.setVisibility(View.VISIBLE);
            }
        });

        dont_have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_layout.setVisibility(View.GONE);
                sigunp_layout.setVisibility(View.VISIBLE);
            }
        });

        register_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_layout.setVisibility(View.GONE);
                sigunp_layout.setVisibility(View.VISIBLE);

            }
        });
    }

    private void registerUser(){

        String user_name = signUpName.getText().toString();
        String user_phone = signUpPhone.getText().toString();
        String user_email = signUpemail.getText().toString().trim();
        String user_password = signUpUserPassword.getText().toString().trim();

        // if... statements to validate these inputs --->

        if(user_name.isEmpty())
        {
            signUpName.setError("Name is required");
            signUpName.requestFocus();
            return;
        }

        else if(user_phone.isEmpty())
        {
            signUpPhone.setError("Phone number is required");
            signUpPhone.requestFocus();
            return;
        }

        else if(user_email.isEmpty())
        {
            signUpemail.setError("Email is required");
            signUpemail.requestFocus();
            return;
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(user_email).matches())
        {
            signUpemail.setError("Invalid email input");
            signUpemail.requestFocus();
            return;
        }

        else if(user_password.isEmpty())
        {
            signUpUserPassword.setError("Password required");
            signUpUserPassword.requestFocus();
            return;
        }

        if(user_password.length() < 6)
        {
            signUpUserPassword.setError("Password with atleast 6 characters is required");
            signUpUserPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(user_email, user_password)

                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {       // check if user has been already registered
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //create an user object so that we that store the user info in a realtime database
                            User user = new User(user_name, user_phone, user_email);

                            // firebase database object
                            // users => collection of objects
                            // add realtime database to your project

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())      //we get the registered users id and set it here, so that we can correspond this object(i.e user) to the registered user
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {

                                            if(task2.isSuccessful()){
//                                                hideProgress();
                                                sendUserToNextActivity();
                                            }
                                            else{

//                                                hideProgress();
                                                Toast.makeText(login_signup.this, ""+task2.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                        else{

//                            hideProgress();
                            Toast.makeText(login_signup.this, ""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void loginUser(){

        final String loggedUser_email = loginUserEmail.getText().toString();
        final String loggedUser_password = loginUserPassword.getText().toString();

        if(loggedUser_email.isEmpty())
        {
            loginUserEmail.setError("Email is required");
            loginUserEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(loggedUser_email).matches())
        {
            loginUserEmail.setError("Invalid email input");
            loginUserEmail.requestFocus();
            return;
        }
        if(loggedUser_password.isEmpty())
        {
            loginUserPassword.setError("Password is required");
            loginUserPassword.requestFocus();
            return;
        }
        if(loggedUser_password.length()<6)
        {
            loginUserPassword.setError("Password with atleast 6 characters is required");
        }

//        progressDialogLogin.setMessage("Logging you in");
//        progressDialogLogin.setTitle("Authorizing...");
//        progressDialogLogin.setCanceledOnTouchOutside(true);
//        progressDialogLogin.show();
        mAuth1.signInWithEmailAndPassword(loggedUser_email, loggedUser_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task1) {
                if(task1.isSuccessful()){




//                    hideProgressLogin();
                    sendUserToNextActivity();

                }
                else{
//                    hideProgressLogin();
                    Toast.makeText(login_signup.this, ""+task1.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

        });



    }


    private void sendUserToNextActivity() {
        Intent i = new Intent(login_signup.this, MainActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
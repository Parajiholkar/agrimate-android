package com.example.agrimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Main_Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(login_signup.PREFS_NAME,0);
                boolean hasRegistered = sharedPreferences.getBoolean("hasRegistered",false);


                Intent i;
                if(hasRegistered){

                    i = new Intent(Main_Splash.this, MainActivity2.class);

                }
                else{
                    i = new Intent(Main_Splash.this, login_signup.class);
                }
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(login_signup.PREF_NAME,0);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);


                Intent i;
                if(hasLoggedIn){

                    i = new Intent(Main_Splash.this, MainActivity.class);

                }
                else{
                    i = new Intent(Main_Splash.this, login_signup.class);
                }
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
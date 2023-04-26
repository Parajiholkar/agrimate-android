package com.example.agrimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    private Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void first(View v){
        button2 = findViewById(R.id.button2);
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

}

package com.example.agrimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Scheme1.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Scheme2.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Scheme3.class);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Scheme4.class);
                startActivity(intent);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Scheme5.class);
                startActivity(intent);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Scheme6.class);
                startActivity(intent);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Scheme7.class);
                startActivity(intent);
            }
        });
    }
//    public void first(View v){
//        button2 = findViewById(R.id.button2);
//        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
//        startActivity(intent);
//    }
//    public void second(View v){
//        button3 = findViewById(R.id.button3);
//        Intent intent = new Intent(this, Scheme1.class);
//        startActivity(intent);
//    }
//    public void third(View v){
//        button4 = findViewById(R.id.button4);
//        Intent intent = new Intent(this, Scheme2.class);
//        startActivity(intent);
//    }
//    public void fourth(View v){
//        button5 = findViewById(R.id.button5);
//        Intent intent = new Intent(this, Scheme3.class);
//        startActivity(intent);
//    }
//    public void fifth(View v){
//        button6 = findViewById(R.id.button6);
//        Intent intent = new Intent(this, Scheme4.class);
//        startActivity(intent);
//    }
//    public void sixth(View v){
//        button7 = findViewById(R.id.button7);
//        Intent intent = new Intent(this, Scheme5.class);
//        startActivity(intent);
//    }
//    public void seventh(View v){
//        button8 = findViewById(R.id.button8);
//        Intent intent = new Intent(this, Scheme6.class);
//        startActivity(intent);
//    }
//    public void eighth(View v){
//        button9 = findViewById(R.id.button9);
//        Intent intent = new Intent(this, Scheme7.class);
//        startActivity(intent);
//    }

}

package com.example.agrimate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    TextView tempera;
    TextView wind;
    TextView temp;
    TextView humidity;
    TextView clouds;
    DecimalFormat df = new DecimalFormat("#.##");
    public static final int REQUEST_CODE = 100 ;
    FusedLocationProviderClient fusedLocationProviderClient ;
    private Button button;
    String address, city, country ; // location is store in this variable

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userId;
    TextView name_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        userId = user.getUid();
        button = findViewById(R.id.button);
        name_display =findViewById(R.id.userName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

        databaseReference.child(String.valueOf(userId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){

                    String name = userProfile.user_name;

                    name_display.setText("Hi, " + name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // comment
        temp = findViewById(R.id.temp);
        humidity = findViewById(R.id.humidity);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

//    public void scheme(View v){
//        button = findViewById(R.id.button);
//        Intent intent = new Intent(this,MainActivity2.class);
//        startActivity(intent);
//        // Comment
//    }
    public void getLocation(){

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                address = addresses.get(0).getAddressLine(0);
                                city = addresses.get(0).getLocality();
                                country = addresses.get(0).getCountryName();
                            } catch (IOException e) {
                                throw new RuntimeException(e) ;
                            }
                        }
                    });
        }else {

            askPermission();

        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ){

                getLocation();
            }else {
                Toast.makeText(this, "Please Give The Location Permission", Toast.LENGTH_SHORT).show();

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void get(View view) {
        String apiKey = "c9cd744e5b3bf062bcb1d4ac436d8cce";
        String city = "delhi";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=090b19c16593a3bf6b3e993e3e171874";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String toutput = "";
//                String woutput = "";
                String houtput = "";
//                String coutput = "";
                try {
                    JSONObject object = response.getJSONObject("main");
                    Double temperature1 = object.getDouble("temp") -273.15;
                    int humi = object.getInt("humidity");
//                    JSONObject jsonObjectWind = object.getJSONObject("wind");
//                    String wi = jsonObjectWind.getString("speed");
//                    JSONObject jsonObjectCloud = object.getJSONObject("clouds");
//                    String clo = jsonObjectCloud.getString("all");
                    toutput += df.format(temperature1) + " C";
                    temp.setText(toutput);
//                    woutput += wi + "m/s (meters per sec)";
                    houtput += humi + "%";
//                    coutput += clo + "%";
                    humidity.setText(houtput);
//                    wind.setText(woutput);
//                    clouds.setText(coutput);

//
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }
}


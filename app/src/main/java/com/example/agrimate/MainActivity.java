package com.example.agrimate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
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

    ArrayList<trending_Disease_model> arrayList = new ArrayList<>() ;
    RecyclerView recyclerView ;

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
        clouds = findViewById(R.id.clouds);
        wind = findViewById(R.id.wind);
        temp = findViewById(R.id.temp);
        humidity = findViewById(R.id.humidity);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        recyclerView = findViewById(R.id.recycler_disease);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String TD1_title = "प्रकंद और जड़ सड़न (Rhizome and Root Rot)" ;
        String TD1_discription = "पौधों में पानी की अधिकता (Overwatering) हो जाने से मिट्टी अधिक गीली (Soggy) और संकुचित (Compressed) हो जाती है जिसके कारण मिट्टी में उपस्थित वायु रंध्र (Air Holes) बंद हो जाते हैं। पौधों की जड़ों को स्वस्थ रहने के लिए ऑक्सीजन की जरूरत पड़ती है लेकिन मिट्टी में उपस्थिंत वायु रंध्र बंद होने से जड़ें, मिट्टी से ऑक्सीजन ग्रहण नहीं कर पाती हैं जिसके कारण जड़ें गलने और सड़ने लगती हैं। इस रोग में जड़ों के नष्ट (Roots Decay) होने के कारण पौधे को पर्याप्त पोषक तत्व नहीं मिल पाते हैं, जिसके कारण पौधे की ग्रोथ रुक जाती है और वह धीरे धीरे नष्ट होने लगता है। इसे ही जड़ सड़न या जड़ गलन रोग (Root Rot Diseases) कहते हैं। आइये जानते हैं पौधों में जड़ सड़न रोग के लक्षण के बारे में:\n" +
                "\n" +
                "1) पौधे की ग्रोथ रुक जाना\n" +
                "2) पत्तियों का पीला पड़ना\n" +
                "3) पौधे की पत्तियों का मुरझाना\n" +
                "4) जड़ों का गलना तथा पौधा मुरझाया हुआ दिखाई देना।\n";
        String TD2_title = "गुलाबी बॉलवॉर्म (Pink bollworm)" ;
        String TD2_discription = "\nगुलाबी बॉलवॉर्म ( पेक्टिनोफोरा गॉसीपिएला ; स्पैनिश : लगर्टा रोसाडा ) एक कीट है जो कपास की खेती में एक कीट के रूप में जाना जाता है। वयस्क झालरदार पंखों वाला छोटा, पतला, धूसर पतंगा होता है। लार्वा एक सुस्त सफेद कैटरपिलर है जिसके आठ जोड़े पैर हैं इसके पृष्ठ भाग के साथ विशिष्ट गुलाबी बैंडिंग है। लार्वा लंबाई में आधा इंच तक पहुंचता है।" +
                "\nमादा कीट रुई के गोलों में अंडे देती है और जब अंडों से लार्वा निकलता है तो वे खाकर नुकसान पहुंचाते हैं। वे बीजों को खाने के लिए रुई के फाहे को चबाते हैं। चूंकि कपास का उपयोग फाइबर और बीज के तेल दोनों के लिए किया जाता है , इसलिए नुकसान दुगुना होता है।" +
                "\nअमेरिकी कपास स्वदेशी किस्मों की तुलना में अधिक क्षतिग्रस्त हैं । फसल का मध्य चरण पीड़ित है और फसल के अंत तक जारी रहता है। गुलाबी बॉलवर्म मोनोफैगस कीट है और केवल कपास पर हमला करता है।\n\n";

        String TD3_title = "मायरोथिसिम पत्ती धब्बा या झुलसा रोग" ;
        String TD3_discription = "\nरोगकारक: यह रोग बीज एंव मृदा जनित मायरोथिसिम रोरीडम नामक फफूंद से होता है ।\n" +
                "\n" +
                "रोग के लक्षण:\n" +
                "\n" +
                "1) मायरोथिसिम  रोग मुख्यतया बीज जनित रोग हैं । इससे संक्रमित पौधों की पत्तियों की उपरी सतह पर गोल या अनियमित आकार के भूरे रंग के धब्बे बनते हैं । इन धब्बों का बाहरी हिस्सा गुलाबी रंग का होता है ।\n" +
                "\n" +
                "2) कई धब्बे आपस में मिलकर बड़ा धब्बा बना लेते हैं । रोग की बाद की अवस्था में इन धब्बो का बीच वाला भाग गिर जाता है तथा इस बचे हुए छेद को ‘शूट होल कहते हैं। ऐसे धब्बे तना, टिण्डे तथा टिण्डे के इतंबजे पर बनते हैं।" +
                "\nनुकसान :\n" +
                "3) अधिक प्रकोप होने पर संक्रमित पौधों का तना टुट जाता हैं। टिण्डे पर धब्बे बनने से रेषा खुरदरा तथा बदरंगा हो जाता हैं।\n" ;

        String TD4_title = "लीफ स्पॉट (leaf spot)" ;
        String TD4_discription = "लीफ स्पॉट रोग कवक या बैक्टीरिया के कारण होते हैं जो पत्तियों को संक्रमित करते हैं, जिससे उन पर धब्बे या घाव विकसित हो जाते हैं। ये रोग पौधों की वृद्धि और उपस्थिति को महत्वपूर्ण रूप से प्रभावित कर सकते हैं, और कुछ मामलों में, यहां तक कि पौधे की मृत्यु भी हो सकती है। लक्षणों में पत्तियों पर छोटे, गोल या अनियमित आकार के धब्बे शामिल हैं जो पीले, भूरे या काले रंग के हो सकते हैं, धब्बे उभरे हुए या धंसे हुए दिखाई दे सकते हैं, उन्नत अवस्था में, धब्बे आपस में मिल सकते हैं और पत्तियों के पीले होने और गिरने का कारण बन सकते हैं, और कुछ मामलों में, धब्बे तनों और फलों पर भी दिखाई दे सकते हैं। कुछ सामान्य लीफ स्पॉट रोगों में सेप्टोरिया लीफ स्पॉट, एन्थ्रेक्नोज और पाउडरी मिल्ड्यू शामिल हैं।" +
                "लीफ स्पॉट रोगों के कारण कुछ सबसे सामान्य हैं:\n" +
                "1) अत्यधिक पानी देना या खराब जल निकासी, जो एक नम वातावरण बना सकता है जो कवक के विकास के लिए अनुकूल है\n" +
                "2) कीड़ों या यांत्रिक चोट से पत्तियों को नुकसान\n" +
                "3) भीड़ भरे या तनावग्रस्त पौधे\n" +
                "4) उच्च आर्द्रता या लंबे समय तक पत्ती का गीलापन\n";

        String TD5_title = "पाउडरी मिल्ड्यू (Powdery Mildew)" ;
        String TD5_discription = " पेड़ पौधों की पत्तियों, तनों सहित अन्य भाग को प्रभावित करने वाला पाउडरी मिल्ड्यू (powdery mildew) रोग एक प्रकार का कवक रोग है, जो विभिन्न प्रकार के पौधों में कवक (fungus) की कई अलग-अलग प्रजातियों के कारण होता है। इसे पाउडरी फफूंदी रोग भी कहा जाता है। विभिन्न प्रकार के पेड़-पौधे जैसे- झाड़ियाँ, बेल वाले पौधे, फल-फूल, सब्जियाँ, घास और खरपतवार इत्यादि इस रोग से प्रभावित हो सकते हैं।\n" +
                "रोग के लक्षण:\n" +
                "1) आमतौर पर पौधे की पत्तियां पीली और मुरझाई हुई दिखाई देती हैं।\n" +
                "2) गार्डन में लगे पौधे में फूल कम संख्या में होते हैं तथा फल की उपज और गुणवत्ता घटने लगती हैं।\n" +
                "3) पौधों की पत्तियों, कलियों, तनों या युवा फलों पर सफेद या ब्राउन रंग के धब्बे दिखाई देते हैं।\n" +
                "4) पाउडरी मिल्ड्यू से संक्रमित फल और फूल अक्सर विकृत या खराब हो जाते हैं।\n" +
                "5)फफूंदी वाले पौधे के हिस्से बौने और विकृत हो सकते हैं।\n";

        String TD6_title = "डाउनी फफूंदी रोग (Downy Mildew disease)" ;
        String TD6_discription = "\nडाउनी फफूंदी एक फंगल रोग है जो कारण बनता है स्यूडोपेरोनोस्पोरा क्यूबेन्सिस और पपीते की फसल के सबसे महत्वपूर्ण पत्तेदार रोगों में से एक है। वातावरण में उच्च नमी और पत्ते पर रोग के पक्ष में है और पपीते की फसल को बड़ा नुकसान होता है।" +
                "\nप्रारंभ में पत्तियों की ऊपरी सतह पर छोटे, अनियमित से कोणीय, थोड़ा हरित क्षेत्र दिखाई देते हैं।\n" +
                "रोग के लक्षण पहले पुरानी पत्तियों पर दिखाई देते हैं और बाद में छोटी पत्तियों की ओर बढ़ते हैं क्योंकि वे विस्तार करते हैं।" +
                "आर्द्रता और पत्तियों की गीलापन अनुकूल है बीजाणु तेजी से और तेजी से बनाने वाले घावों को गुणा करते हैं, पत्तियों के किनारे पर भूरे नीले रंग के साथ, नीचे की उपस्थिति देते हैं।\n" +
                "बड़े आकार के घावों पूरी पत्तियों को कवर किया जाएगा और पीक रोग गंभीरता पर कई बार पत्तियों से छोड़ देंगे ।\n" +
                "संबंधित डंठल में फूल भूरे रंग के हो जाएंगे और छोड़ देंगे।\n\n\n" ;

        String TD7_title = "झुलसा रोग (blight disease)"  ;
        String TD7_discription = "झुलसा रोग (blight) का प्रकोप अनेक फसलों पर होता है, जैसे धान, जौ, कपास, आलू, बैगन आदि। इसके लक्षण पत्तियों, बाली की गर्दन एंव तने की निचली गठानों पर प्रमुख रूप से दिखाई देते हैं। प्रारंभिक अवस्था में निचली पत्तियों पर हल्के बैंगनी रंग के छोटे-छोटे धब्बे बनते हैं जो धीरे-धीरे बढ़कर आँख के समान बीच में चैडे व किनारों पर सँकरे हो जाते हैं। इन धब्बों के बीच का रंग हल्के भूरे रंग होता है। जिससे दानों के भरने पर असर पड़ता है। फसल काल के दौरान जब रात्रि का तापमान 20 से 22 से. व आर्द्रता 95 प्रतिशत से अधिक होती है तब इस रोग का तीव्र प्रकोप होने की संभावना होती है।" +
                "इस रोग का प्रबंधन हम निम्न उपायों से ही कर सकते हैं।\n" +
                "इस रोग के प्रकोप वाले क्षेत्रों में बार-बार एक ही प्रजाति न उगाएं।" +
                "पौधे ऐसी जगह न बोए जहा पिछली फसल के अवषेश पड़े हों या जहां छाया रहती हो।\n" +
                "रोग रोधी किस्मों को प्रथामिकता दें, जैसे पूसा-1460 (उन्नत पूसा बासमती 1)\n" +
                "नाइट्रोजन खाद का प्रयोग अधिक न करें तथा रोपाई के 40 दिन बाद तो बिल्कुल न दें।\n" +
                "खेतों में पानी लगातार न लगाए रखें।\n";

        arrayList.add(new trending_Disease_model(TD1_title,TD1_discription,R.drawable.img_6));
        arrayList.add(new trending_Disease_model(TD2_title,TD2_discription,R.drawable.img_5));
        arrayList.add(new trending_Disease_model(TD3_title,TD3_discription,R.drawable.img_4));
        arrayList.add(new trending_Disease_model(TD4_title,TD4_discription,R.drawable.img_3));
        arrayList.add(new trending_Disease_model(TD5_title,TD5_discription,R.drawable.img_1));
        arrayList.add(new trending_Disease_model(TD6_title,TD6_discription,R.drawable.img_2));
        arrayList.add(new trending_Disease_model(TD7_title,TD7_discription,R.drawable.img));
        Adapter adapter = new Adapter(this,arrayList);
        recyclerView.setAdapter(adapter);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        getLocation();
        get();

    }

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


    public void get() {
        String apiKey = "c9cd744e5b3bf062bcb1d4ac436d8cce";
        String city = "pune";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=090b19c16593a3bf6b3e993e3e171874";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String toutput = "";
                String woutput = "";
                String houtput = "";
                String coutput = "";
                try {
                    JSONObject object = response.getJSONObject("main");
                    Double temperature1 = object.getDouble("temp") -273.15;
                    int humi = object.getInt("humidity");
                    JSONObject jsonObjectWind = response.getJSONObject("wind");
                    String wi = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectCloud = response.getJSONObject("clouds");
                    String clo = jsonObjectCloud.getString("all");
                    toutput += df.format(temperature1) + " C";
                    temp.setText(toutput);
                    woutput += wi + "m/s";
                    houtput += humi + "%";
                    coutput += clo + "%";
                    humidity.setText(houtput);
                    wind.setText(woutput);
                    clouds.setText(coutput);

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


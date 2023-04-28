package com.example.agrimate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Scheme5 extends AppCompatActivity {

    private WebView mywebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme5);
        mywebView = (WebView) findViewById(R.id.webview5);
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.loadUrl("https://www.kisaanhelpline.com/government-scheme/110/PM-Kisan-Maan-Dhan-Yojana-%E0%A4%AA%E0%A5%80%E0%A4%8F%E0%A4%AE-%E0%A4%95%E0%A4%BF%E0%A4%B8%E0%A4%BE%E0%A4%A8-%E0%A4%AE%E0%A4%BE%E0%A4%A8-%E0%A4%A7%E0%A4%A8-%E0%A4%AF%E0%A5%8B%E0%A4%9C%E0%A4%A8%E0%A4%BE-");
        WebSettings webSettings=mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    public class mywebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view,url,favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        if (mywebView.canGoBack()) {
            mywebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

package com.example.agrimate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Scheme3 extends AppCompatActivity {

    private WebView mywebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme3);
        mywebView = (WebView) findViewById(R.id.webview3);
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.loadUrl("https://www.kisaanhelpline.com/government-scheme/113/%E0%A4%AA%E0%A4%B6%E0%A5%81-%E0%A4%95%E0%A4%BF%E0%A4%B8%E0%A4%BE%E0%A4%A8-%E0%A4%95%E0%A5%8D%E0%A4%B0%E0%A5%87%E0%A4%A1%E0%A4%BF%E0%A4%9F-%E0%A4%95%E0%A4%BE%E0%A4%B0%E0%A5%8D%E0%A4%A1-%E0%A4%8B%E0%A4%A3-%E0%A4%AF%E0%A5%8B%E0%A4%9C%E0%A4%A8%E0%A4%BE");
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

package com.example.sub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sub.R;

public class Go_web extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.web );
        webview=(WebView)findViewById(R.id.web2);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        openURL();
    }
    private void openURL() {
        webview.loadUrl("https://trickgame24h.blogspot.com/p/chuyentrang.html");
        webview.requestFocus();
    }
}
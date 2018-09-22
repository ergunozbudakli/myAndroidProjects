package com.ergunozbudakli.mini;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends AppCompatActivity {
WebView webView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView4=findViewById(R.id.webView4);
        webView4.setWebViewClient(new WebViewClient() { @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) { return super.shouldOverrideUrlLoading( view, request ); } });
        webView4.getSettings().setJavaScriptEnabled(true);
        webView4.loadUrl("http://www.miniyazilim.com/");
    }
}

package com.ergunozbudakli.mini;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Donanim extends AppCompatActivity {
WebView webView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donanim);
        webView2=findViewById(R.id.webView2);
        webView2.setWebViewClient(new WebViewClient() { @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) { return super.shouldOverrideUrlLoading( view, request ); } });
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.loadUrl("http://www.minibilisim.com.tr//minibilgisayar");
    }
}

package com.ergunozbudakli.mini;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Iletisim extends AppCompatActivity {
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim);
        webView=findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() { @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) { return super.shouldOverrideUrlLoading( view, request ); } });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.minibilisim.com/iletisim");
    }
}

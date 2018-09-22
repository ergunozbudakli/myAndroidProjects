package com.ergunozbudakli.mini;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Netsis extends AppCompatActivity {
WebView webView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netsis);
        webView3=findViewById(R.id.webView3);
        webView3.setWebViewClient(new WebViewClient() { @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) { return super.shouldOverrideUrlLoading( view, request ); } });
        webView3.getSettings().setJavaScriptEnabled(true);
        webView3.loadUrl("http://www.minibilisim.com.tr/donanim/netsis-ve-erp");
    }
}

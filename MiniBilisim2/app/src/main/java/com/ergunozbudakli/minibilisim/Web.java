package com.ergunozbudakli.minibilisim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.anasayfa){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.iletisim){
            Intent intent=new Intent(getApplicationContext(),Iletisim.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.hakkimizda){
            Intent intent=new Intent(getApplicationContext(),Hakkimizda.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}


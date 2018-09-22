package com.ergunozbudakli.mini3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ergunozbudakli.mini3.Donanim;
import com.ergunozbudakli.mini3.Hakkimizda;
import com.ergunozbudakli.mini3.Iletisim;
import com.ergunozbudakli.mini3.Netsis;
import com.ergunozbudakli.mini3.R;
import com.ergunozbudakli.mini3.ViewPagerAdapter;
import com.ergunozbudakli.mini3.Web;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.ViewPager);
        ViewPagerAdapter imageAdapter= new ViewPagerAdapter(this);
        viewPager.setAdapter(imageAdapter);
        Timer timer= new Timer();
        timer.scheduleAtFixedRate(new Mytimertask(),2000,4000);
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
        else if(item.getItemId()== R.id.iletisim){
            Intent intent=new Intent(getApplicationContext(),Iletisim.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.hakkimizda){
            Intent intent=new Intent(getApplicationContext(),Hakkimizda.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void web(View view){

        Intent intent= new Intent(getApplicationContext(),Web.class);
        startActivity(intent);

    }

    public void donanim(View view){

        Intent intent= new Intent(getApplicationContext(),Donanim.class);
        startActivity(intent);

    }
    public void netsis(View view){

        Intent intent= new Intent(getApplicationContext(),Netsis.class);
        startActivity(intent);

    }

    public class Mytimertask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }else if(viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(3);
                    }else if(viewPager.getCurrentItem()==3){
                        viewPager.setCurrentItem(4);
                    }else if(viewPager.getCurrentItem()==4){
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}

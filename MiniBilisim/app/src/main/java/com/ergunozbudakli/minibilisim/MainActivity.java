package com.ergunozbudakli.minibilisim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.ViewPager);
        ImageAdapter imageAdapter= new ImageAdapter(this);
        viewPager.setAdapter(imageAdapter);
        Timer timer= new Timer();
        timer.scheduleAtFixedRate(new Mytimertask(),2000,4000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void kurumsal(View view){
        Intent intent= new Intent(this,Hakkimizda.class);
        startActivity(intent);
    }
    public class Mytimertask extends TimerTask{

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

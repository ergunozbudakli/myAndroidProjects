package com.ergunozbudakli.catchtokenny;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView textView2;

    int score=0;
    ImageView kenny1;
    ImageView kenny2;
    ImageView kenny3;
    ImageView kenny4;
    ImageView kenny5;
    ImageView kenny6;
    ImageView kenny7;
    ImageView kenny8;
    ImageView kenny9;
    Handler handler;
    Runnable run;
    ImageView[] imageArrays;
    Button button;
    Button button1;
    int highscore;
    static SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);

        kenny1=findViewById(R.id.imageView);
         kenny2=findViewById(R.id.imageView1);
         kenny3=findViewById(R.id.imageView2);
         kenny4=findViewById(R.id.imageView3);
         kenny5=findViewById(R.id.imageView4);
         kenny6=findViewById(R.id.imageView5);
         kenny7=findViewById(R.id.imageView6);
         kenny8=findViewById(R.id.imageView7);
         kenny9=findViewById(R.id.imageView8);


        imageArrays= new ImageView[]{kenny1,kenny2,kenny3,kenny4,kenny5,kenny6,kenny7,kenny8,kenny9};
        for(ImageView image:imageArrays){
            image.setVisibility(View.INVISIBLE);
        }
        textView.setVisibility(View.INVISIBLE);

        try {
            database= getApplicationContext().openOrCreateDatabase("HighScore",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS highScore(highscore LONG)");
            Cursor cursor= database.rawQuery("SELECT * FROM highScore",null);
            cursor.moveToFirst();
            int highIx= cursor.getColumnIndex("highscore");
            int yuksek=cursor.getInt(highIx);
            highscore=yuksek;


        }catch (Exception e){
e.printStackTrace();
        }

        textView2.setText(""+highscore);
        int x=Integer.parseInt(textView2.getText().toString());
        if(x==0){
            database.execSQL("CREATE TABLE IF NOT EXISTS highScore(highscore LONG)");
            database.execSQL("INSERT INTO highScore(highscore) VALUES (0)");
        }


        button1=findViewById(R.id.button2);
        button1.setVisibility(View.INVISIBLE);





    }
    public void start(View view){

        button= findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long milisUntilFinished) {
                textView.setText("Time: "+milisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                button1.setVisibility(View.VISIBLE);
                textView.setText("Time's Off");
                handler.removeCallbacks(run);
                for(ImageView image:imageArrays){
                    image.setVisibility(View.INVISIBLE);
                }
                if(score>highscore){
                    highscore=score;
                try {
                    database= getApplicationContext().openOrCreateDatabase("HighScore",MODE_PRIVATE,null);
                    database.execSQL("CREATE TABLE IF NOT EXISTS highScore(highscore LONG)");
                    SQLiteStatement statement= database.compileStatement("UPDATE highScore SET highscore= ?");

                    statement.bindLong(1,highscore);
                    statement.execute();

                }catch (Exception e){
                e.printStackTrace();
                }

                }
                textView2.setText(""+highscore);
            }
        }.start();

        action();

    }
    public void retry(View view){
        score=0;
        final TextView textView1=findViewById(R.id.textView1);
        textView1.setText("Score: "+score);

        button= findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        button1.setVisibility(View.INVISIBLE);
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long milisUntilFinished) {
                textView.setText("Time: "+milisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                button1.setVisibility(View.VISIBLE);
                textView.setText("Time's Off");
                handler.removeCallbacks(run);
                for(ImageView image:imageArrays){
                    image.setVisibility(View.INVISIBLE);
                }
                if(score>highscore){
                    highscore=score;
                    try {
                        database= getApplicationContext().openOrCreateDatabase("HighScore",MODE_PRIVATE,null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS highScore(highscore LONG)");
                        SQLiteStatement statement=database.compileStatement("UPDATE highScore SET highscore= ?");
                        statement.bindLong(1,highscore);
                        statement.execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    textView2.setText(""+highscore);
                }
            }
        }.start();

        action();

    }
    public void winscore(View view){
        score++;
        TextView textView1=findViewById(R.id.textView1);
        textView1.setText("Score: "+score);
    }
    public void action(){
        handler=new Handler();
        run=new Runnable() {
            @Override
            public void run() {

                for(ImageView image:imageArrays){
                    image.setVisibility(View.INVISIBLE);
                }
                Random r=new Random();
                int i=r.nextInt(8-0);
                imageArrays[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this,500);
            }
        };
handler.post(run);
    }
}

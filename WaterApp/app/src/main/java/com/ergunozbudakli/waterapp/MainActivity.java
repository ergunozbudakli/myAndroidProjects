package com.ergunozbudakli.waterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static SQLiteDatabase database;
    boolean a;
    static ArrayList<String> dates;
    ImageView imageView;
    ImageView imageView1;
    EditText editText;
    TextView textView;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    Button button3;
    Button button;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView1);
        imageView1=findViewById(R.id.imageView2);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);
        button3=findViewById(R.id.button3);
        button2=findViewById(R.id.button2);
        button=findViewById(R.id.button);
        imageView.setVisibility(View.INVISIBLE);
        imageView1.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);
        textView5.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        dates= new ArrayList<String>();
    }
    public  void add(View view){

        button.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        imageView1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView3.setVisibility(View.VISIBLE);
        textView4.setVisibility(View.VISIBLE);
        textView5.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText.setText("");
        a=false;
        Toast.makeText(this,"Lütfen içtiğin miktarı seç",Toast.LENGTH_LONG).show();

        }
    public void bardak(View view){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Doğrulama ekranı");
        alert.setMessage("1 bardak su içtiğine emin misin?");
        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                button.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                imageView1.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                textView4.setVisibility(View.INVISIBLE);
                textView5.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                a=false;
                Toast.makeText(getApplicationContext(),"İçmiş olduğun bir bardak su veritabanına kaydedilmiştir.",Toast.LENGTH_LONG).show();
                int quantity=200;
                SimpleDateFormat spf= new SimpleDateFormat("yyyy-MM-dd");
                String date= spf.format(new Date());
                try {

                    database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                    database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                    Cursor cursor= database.rawQuery("SELECT * FROM drink",null);
                    int dateIx=cursor.getColumnIndex("date");
                    cursor.moveToFirst();
                    while (cursor!=null){
                        dates.add(cursor.getString(dateIx));
                        cursor.moveToNext();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                for(int i=0;i<dates.size();i++){
                    if(dates.get(i).equalsIgnoreCase(date)){
                        a=true;
                    }
                }


                if(a==false){
                    try {
                        database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                        SQLiteStatement statement=database.compileStatement("INSERT INTO drink(date,quantity) VALUES (?,?)");
                        statement.bindString(1,date);
                        statement.bindString(2,String.valueOf(quantity));
                        statement.execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        int newquantity = 0;
                        database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                        SQLiteStatement statement=database.compileStatement("UPDATE drink SET quantity=? WHERE date=?");
                        Cursor cursor=database.rawQuery("SELECT * FROM drink WHERE date='"+date+"'",null);
                        int quantityIx=cursor.getColumnIndex("quantity");
                        cursor.moveToFirst();
                        newquantity=newquantity+(Integer.parseInt(cursor.getString(quantityIx))+200);
                        statement.bindString(1,String.valueOf(newquantity));
                        statement.bindString(2,date);
                        statement.execute();



                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });
        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }
    public void sise(View view){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Doğrulama ekranı");
        alert.setMessage("1 şişe su içtiğine emin misin?");
        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                button.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                imageView1.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                textView4.setVisibility(View.INVISIBLE);
                textView5.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"İçmiş olduğun bir şişe su veritabanına kaydedilmiştir.",Toast.LENGTH_LONG).show();
                a=false;
                int quantity=750;
                SimpleDateFormat spf= new SimpleDateFormat("yyyy-MM-dd");
                String date= spf.format(new Date());
                try {

                    database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                    database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                    Cursor cursor= database.rawQuery("SELECT * FROM drink",null);
                    int dateIx=cursor.getColumnIndex("date");
                    cursor.moveToFirst();
                    while (cursor!=null){
                        dates.add(cursor.getString(dateIx));
                        cursor.moveToNext();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                for(int i=0;i<dates.size();i++){
                    if(dates.get(i).equalsIgnoreCase(date)){
                        a=true;
                    }
                }


                if(a==false){
                    try {
                        database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                        SQLiteStatement statement=database.compileStatement("INSERT INTO drink(date,quantity) VALUES (?,?)");
                        statement.bindString(1,date);
                        statement.bindString(2,String.valueOf(quantity));
                        statement.execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        int newquantity = 0;
                        database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                        SQLiteStatement statement=database.compileStatement("UPDATE drink SET quantity=? WHERE date=?");
                        Cursor cursor=database.rawQuery("SELECT * FROM drink WHERE date='"+date+"'",null);
                        int quantityIx=cursor.getColumnIndex("quantity");
                        cursor.moveToFirst();
                        newquantity=newquantity+(Integer.parseInt(cursor.getString(quantityIx))+750);
                        statement.bindString(1,String.valueOf(newquantity));
                        statement.bindString(2,date);
                        statement.execute();



                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });
        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();

    }
    public void kaydet(View view){

        if(editText.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Lütfen bir miktar girdikten sonra kaydet tuşuna basın.",Toast.LENGTH_LONG).show();
        }else {
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Doğrulama ekranı");
            alert.setMessage(editText.getText().toString()+" ml su içtiğine emin misin?");
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    button.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    imageView1.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    textView3.setVisibility(View.INVISIBLE);
                    textView4.setVisibility(View.INVISIBLE);
                    textView5.setVisibility(View.INVISIBLE);
                    button3.setVisibility(View.INVISIBLE);
                    editText.setVisibility(View.INVISIBLE);

                    a=false;
                    Toast.makeText(getApplicationContext(),"İçmiş olduğun miktar veritabanına kaydedilmiştir.",Toast.LENGTH_LONG).show();
                    int quantity=Integer.parseInt(editText.getText().toString());
                    SimpleDateFormat spf= new SimpleDateFormat("yyyy-MM-dd");
                    String date= spf.format(new Date());
                    try {

                        database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                        Cursor cursor= database.rawQuery("SELECT * FROM drink",null);
                        int dateIx=cursor.getColumnIndex("date");
                        cursor.moveToFirst();
                        while (cursor!=null){
                            dates.add(cursor.getString(dateIx));
                            cursor.moveToNext();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    for(int i=0;i<dates.size();i++){
                        if(dates.get(i).equalsIgnoreCase(date)){
                            a=true;
                        }
                    }


                    if(a==false){
                        try {
                            database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                            database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                            SQLiteStatement statement=database.compileStatement("INSERT INTO drink(date,quantity) VALUES (?,?)");
                            statement.bindString(1,date);
                            statement.bindString(2,String.valueOf(quantity));
                            statement.execute();

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            int newquantity = 0;
                            database= getApplicationContext().openOrCreateDatabase("Water",MODE_PRIVATE,null);
                            database.execSQL("CREATE TABLE IF NOT EXISTS drink (date DATE,quantity VARCHAR)");
                            SQLiteStatement statement=database.compileStatement("UPDATE drink SET quantity=? WHERE date=?");
                            Cursor cursor=database.rawQuery("SELECT * FROM drink WHERE date='"+date+"'",null);
                            int quantityIx=cursor.getColumnIndex("quantity");
                            cursor.moveToFirst();
                            newquantity=newquantity+(Integer.parseInt(cursor.getString(quantityIx))+Integer.parseInt(editText.getText().toString()));
                            statement.bindString(1,String.valueOf(newquantity));
                            statement.bindString(2,date);
                            statement.execute();



                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            });
            alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
            }

    }
    public void detaylar(View view){
        Intent intent= new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}

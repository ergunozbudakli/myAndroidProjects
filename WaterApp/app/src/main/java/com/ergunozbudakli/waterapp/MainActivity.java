package com.ergunozbudakli.waterapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static SQLiteDatabase database;
    boolean a;
    static ArrayList<String> dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dates= new ArrayList<String>();
    }
    public  void add(View view){
        int quantity=750;
        a=false;
        SimpleDateFormat spf= new SimpleDateFormat("yyyy-MM-dd");
        String date= spf.format(new Date());
try {

    database= this.openOrCreateDatabase("Water",MODE_PRIVATE,null);
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
                database= this.openOrCreateDatabase("Water",MODE_PRIVATE,null);
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
        database= this.openOrCreateDatabase("Water",MODE_PRIVATE,null);
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
    public void detaylar(View view){
        Intent intent= new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}

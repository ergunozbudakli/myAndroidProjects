package com.ergunozbudakli.waterapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
ListView listView;
    static ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=findViewById(R.id.listView);
        ArrayList<String> datalar=new ArrayList<>();
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datalar){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
        try {
            MainActivity.database=this.openOrCreateDatabase("Water",MODE_PRIVATE,null);
            Cursor cursor=MainActivity.database.rawQuery("SELECT * FROM drink",null);
            int dateIx=cursor.getColumnIndex("date");
            int quantityIx=cursor.getColumnIndex("quantity");
            cursor.moveToFirst();
            while (cursor !=null){

                String data="DATE: "+cursor.getString(dateIx)+"    Quantity: "+cursor.getString(quantityIx)+"ml";

                datalar.add(0,data);
                cursor.moveToNext();
            }
            arrayAdapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

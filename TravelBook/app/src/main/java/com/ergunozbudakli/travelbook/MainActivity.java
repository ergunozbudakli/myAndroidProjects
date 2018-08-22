package com.ergunozbudakli.travelbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listView;
    static ArrayList<String> names ;
    static ArrayList<LatLng> locations;
    static ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        names= new ArrayList<>();
        locations=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,names);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        try {
            MapsActivity.database= this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            MapsActivity.database.execSQL("CREATE TABLE IF NOT EXISTS places(name VARCHAR, latitude VARCHAR, longitude VARCHAR)");
            Cursor cursor= MapsActivity.database.rawQuery("SELECT * FROM places",null);
            int nameIx=cursor.getColumnIndex("name");
            int latitudeIx=cursor.getColumnIndex("latitude");
            int longitudeIx= cursor.getColumnIndex("longitude");
            cursor.moveToFirst();
            while (true){
                names.add(cursor.getString(nameIx));
                double l1= Double.parseDouble(cursor.getString(latitudeIx));
                double l2= Double.parseDouble(cursor.getString(longitudeIx));
                LatLng loc= new LatLng(l1,l2);
                locations.add(loc);
                cursor.moveToNext();


            }



        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position=i;
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete Screen");
                alert.setMessage("Do you want to delete this location?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MapsActivity.database= getApplicationContext().openOrCreateDatabase("Places",MODE_PRIVATE,null);
                        MapsActivity.database.execSQL("CREATE TABLE IF NOT EXISTS places(name VARCHAR, latitude VARCHAR, longitude VARCHAR)");
                        SQLiteStatement statement=MapsActivity.database.compileStatement("DELETE FROM places WHERE name=?");
                        statement.bindString(1,names.get(position));
                        names.remove(position);
                        locations.remove(position);
                        statement.execute();
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();




                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("position",i);
                intent.putExtra("type","old");
                startActivity(intent);
            }
        }

        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_location,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_location){
            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
            intent.putExtra("type","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

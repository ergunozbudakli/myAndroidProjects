package com.ergunozbudakli.travelbook;

import android.content.Intent;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);

    }
    public void save(View view){
        Intent intent1= getIntent();
        String name=intent1.getStringExtra("name");
        String s=editText.getText().toString();
        MapsActivity.database= this.openOrCreateDatabase ("Places",MODE_PRIVATE,null);
        MapsActivity.database.execSQL("CREATE TABLE IF NOT EXISTS places (name VARCHAR, latitude VARCHAR, longitude VARCHAR)");
        if(!s.equalsIgnoreCase("")){
        SQLiteStatement statement= MapsActivity.database.compileStatement("UPDATE places SET name=? WHERE name=?");
        statement.bindString(1,editText.getText().toString());
        statement.bindString(2,name);
        statement.execute();

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);}
        else{
            SQLiteStatement statement=MapsActivity.database.compileStatement("DELETE FROM places WHERE name=?");
            statement.bindString(1,name);
            statement.execute();
            Toast.makeText(this,"Please write a location name",Toast.LENGTH_SHORT).show();
        }

    }
}

package com.ergunozbudakli.cakmagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CakmagramScreen extends AppCompatActivity {
    ListView listView;
    ArrayList<String> namesFromParse;
    ArrayList<String> commentFromParse;
    ArrayList<Bitmap> imagesFromParse;
    PostClass postClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cakmagram_screen);
        listView=findViewById(R.id.listView);
        namesFromParse=new ArrayList<>();
        commentFromParse=new ArrayList<>();
        imagesFromParse=new ArrayList<>();

        postClass=new PostClass(namesFromParse,imagesFromParse,commentFromParse,this);
        listView.setAdapter(postClass);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String comment=commentFromParse.get(position);
                ParseObject object=new ParseObject("Comments");
                try {
                    object.delete();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                object.deleteInBackground();
                return false;

            }
        });
        download();
    }
    public void download(){
        ParseQuery query= new ParseQuery("Comments");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    if(objects.size()>0){
                        for(final ParseObject object:objects){
                            ParseFile parseFile= object.getParseFile("images");
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e==null && data!=null){
                                        Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                        imagesFromParse.add(bitmap);
                                        namesFromParse.add(object.getString("username"));
                                        commentFromParse.add(object.getString("comment"));
                                        postClass.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            Intent intent=new Intent(getApplicationContext(),PostScreen.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.logout){
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!=null) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Hesaptan çıkılmıştır.", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),LogInScreen.class);
                        startActivity(intent);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

}

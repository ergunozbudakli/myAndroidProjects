package com.ergunozbudakli.cakmagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostScreen extends AppCompatActivity {
    EditText editText;
    ImageView imageView;
    Bitmap selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_screen);
        imageView=findViewById(R.id.post_screen_imageView);
        editText=findViewById(R.id.post_screen_comment_text);

    }
    public void click(View view){
        imageView.setClickable(false);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else {
            Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2 && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();

            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                imageView.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void post(View view){
        SimpleDateFormat spf= new SimpleDateFormat("yyyy-MM-dd- HH:mm");
        String date= spf.format(new Date());
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] bytes= byteArrayOutputStream.toByteArray();
        ParseFile parseFile=new ParseFile("image.png",bytes);

        ParseObject object= new ParseObject("Comments");
        object.put("comment",editText.getText().toString());
        object.put("images",parseFile);
        object.put("username", ParseUser.getCurrentUser().getUsername());
        object.put("createdate",date);
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Post oluşturuldu.",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),CakmagramScreen.class);
                    startActivity(intent);
                }
            }
        });


    }
}

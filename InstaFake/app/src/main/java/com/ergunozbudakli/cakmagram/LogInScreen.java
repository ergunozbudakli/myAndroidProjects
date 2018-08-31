package com.ergunozbudakli.cakmagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LogInScreen extends AppCompatActivity {
EditText username;
EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        username=findViewById(R.id.logIn_screen_name_text);
        password=findViewById(R.id.logIn_screen_password_text);
        if(ParseUser.getCurrentUser()!=null){
            Intent intent= new Intent(getApplicationContext(),CakmagramScreen.class);
            startActivity(intent);
        }

    }
    public void login(View view){

        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Hoşgeldin "+ user.getUsername(),Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),CakmagramScreen.class);
                    startActivity(intent);
                }
            }
        });

    }
    public void signup(View view){
        ParseUser user=new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Kullanıcı oluşturuldu.",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),CakmagramScreen.class);
                    startActivity(intent);
                }
            }
        });
    }
}

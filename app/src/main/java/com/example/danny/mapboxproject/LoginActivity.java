package com.example.danny.mapboxproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userBox = (EditText) findViewById(R.id.editText);
        final EditText passBox = (EditText) findViewById(R.id.editText2);

        //LOGIN
        final Button loginbtn = (Button) findViewById(R.id.button);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                String user = userBox.getText().toString();
                String pass = passBox.getText().toString();

                if(user.equals("user") && pass.equals("pass")){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login errado.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //REGISTAR
        Button registarbtn = (Button) findViewById(R.id.button2);
        registarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
            }
        });

        //FACEBOOK
        ImageButton facebookbtn = (ImageButton) findViewById(R.id.button3);
        facebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //startActivity(new Intent(Login.this, MainActivity.class));
                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Desbloqueei o ponto.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);*/

            }
        });
    }
}
package com.example.danny.mapboxproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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
                    startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
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
                //startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }
}

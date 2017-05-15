package com.example.danny.mapboxproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        //REGISTAR
        Button registarbtn2 = (Button) findViewById(R.id.button4);
        registarbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                startActivity(new Intent(RegistActivity.this, LoginActivity.class));
            }
        });
    }
}

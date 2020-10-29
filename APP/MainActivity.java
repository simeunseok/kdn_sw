package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    private Button cctv;
    private Button notification;
    private Button env;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cctv = findViewById(R.id.CCTV);
        notification=findViewById(R.id.alarm);
        env=findViewById(R.id.envrionment);

        cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,video.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,notification.class);
                startActivity(intent);
            }
        });

        env.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,environment.class);
                startActivity(intent);
            }
        });
    }


}
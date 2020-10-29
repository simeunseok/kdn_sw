package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class notification extends AppCompatActivity {


    private String strTime;
    private static Handler mhandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
                TimeZone area = TimeZone.getTimeZone("Asia/Seoul");
                sdf.setTimeZone(area);
                strTime = sdf.format(cal.getTime());
            }
        };

        TextView alarmHistory = (TextView) findViewById(R.id.alarmContent);

        String msg = "\n";
        String Date = "2020/10/25  ";

        try {
            Thread.sleep(1000);
            mhandler.sendEmptyMessage(0);

            while (true) {
                Intent intent = getIntent();

                int not = intent.getIntExtra("notification", 0);
                if (not > 0 && not < 6) {
                    String reason = intent.getStringExtra("reason");
                    Double val = intent.getDoubleExtra("value", 0);
                    String nowMsg = reason + "  " + Double.toString(val) + "\n";
                    msg = msg + Date + nowMsg + "\n";
                    alarmHistory.setText(msg);
                    break;
                } else if (not == 6) {
                    String reason = intent.getStringExtra("reason");
                    String nowMsg = reason + "\n";
                    msg = msg + Date + nowMsg + "\n";
                    alarmHistory.setText(msg);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

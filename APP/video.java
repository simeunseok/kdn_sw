package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.webkit.WebSettings;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import android.webkit.WebView;
import android.webkit.WebViewClient;




import android.os.Bundle;

public class video extends AppCompatActivity {

    private String strTime;
    private static Handler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        final TextView timeText = findViewById(R.id.cctvDateTime);

        WebView webView=(WebView)findViewById(R.id.cctvVideo);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("http://168.131.150.80:8081/stream/video.mjpeg");

        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
                TimeZone area=TimeZone.getTimeZone("Asia/Seoul");
                sdf.setTimeZone(area);
                strTime = sdf.format(cal.getTime());
            }
        };;

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(1000);
                        timeText.setText(strTime);
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }

                    mhandler.sendEmptyMessage(0) ;
                }
            }
        }

        NewRunnable nr = new NewRunnable() ;
        Thread t = new Thread(nr) ;
        t.start() ;
    }
}
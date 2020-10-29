package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.*;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

public class environment extends AppCompatActivity {
    private Button connect_btn;
    private EditText ipAddress, ip_edit;
    private TextView tempVal, humVal, inflaVal, carbonVal, illuVal, infraVal, IRVLVal, fireText;
    Double t, h, l, c, i, r, f;

    private Socket socket;

    private DataInputStream dis;

    // private String ip = "192.168.0.234";             // IP 번호
    private String ip = "168.131.150.80";
    private int port = 5003;                         // port 번호

    private Bitmap mLargeIcon;
    private PendingIntent mPendingIntent;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    private Intent intent;

    private String occur = "Fire";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        connect_btn = (Button) findViewById(R.id.connect);

        //ip_edit = (EditText) findViewById(R.id.ipEnter);
        tempVal = (TextView) findViewById(R.id.tempVal);
        humVal = (TextView) findViewById(R.id.humVal);
        carbonVal = (TextView) findViewById(R.id.carbonVal);
        illuVal = (TextView) findViewById(R.id.illuVal);
        inflaVal = (TextView) findViewById(R.id.inflaVal);
        IRVLVal = (TextView) findViewById(R.id.IRVLVal);
        infraVal = (TextView) findViewById(R.id.infraVal);


        mBuilder = new NotificationCompat.Builder(this, "111")
                .setSmallIcon(R.drawable.fire)
                .setContentTitle("ESS Fire Alarm")
                .setContentText("화재 발생할 가능성이 있습니다.")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setLargeIcon(mLargeIcon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(mPendingIntent);

        mLargeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
        mPendingIntent = PendingIntent.getActivity(environment.this, 0,
                new Intent(getApplicationContext(), environment.class),
                PendingIntent.FLAG_UPDATE_CURRENT);


        int IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel("111", "alarm", IMPORTANCE);
            mNotificationManager.createNotificationChannel(channel);
        }

        intent = new Intent(environment.this, notification.class);

        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }


    void connect() {
        Thread checkUpdate = new Thread() {
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    dis = new DataInputStream(socket.getInputStream());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                while (true) {
                    try {
                        Thread.sleep(3000);

                        while (true) {
                            String line = (String) dis.readUTF();
                            String a[] = line.split("values");
                            String val[] = a[1].split(", ");
                            String temp[] = val[7].split("]");
                            String fireAlarm = val[7].substring(10, 14);
                            Log.w("fire", fireAlarm);


                            if (fireAlarm.equals(occur)) {
                                Log.w("f", "화재");
                                mNotificationManager.notify(111, mBuilder.build());
                                intent.putExtra("notification", 6);
                                intent.putExtra("reason", "Fire");
                                startActivity(intent);
                                break;
                            }

                            carbonVal.setText(val[1]);
                            IRVLVal.setText(val[2]);
                            inflaVal.setText(val[3]);
                            humVal.setText(val[4]);
                            infraVal.setText(val[5]);
                            illuVal.setText(val[6]);
                            tempVal.setText(temp[0]);

                            c = Double.valueOf(val[1]);
                            f = Double.valueOf(val[2]);
                            l = Double.valueOf(val[3]);
                            h = Double.valueOf(val[4]);
                            r = Double.valueOf(val[5]);
                            i = Double.valueOf(val[6]);
                            t = Double.valueOf(temp[0]);


                            if (f > 300 || l > 300 || c > 300 || i > 500 || t > 70) {

                                mNotificationManager.notify(111, mBuilder.build());

                                if (f > 300) {
                                    intent.putExtra("notification", 1);
                                    intent.putExtra("reason", "IRVL");
                                    intent.putExtra("value", f);
                                    startActivity(intent);
                                    break;
                                } else if (i > 500) {
                                    intent.putExtra("notification", 2);
                                    intent.putExtra("reason", "illuminance");
                                    intent.putExtra("value", i);
                                    startActivity(intent);
                                    break;
                                } else if (t > 70) {
                                    intent.putExtra("notification", 3);
                                    intent.putExtra("reason", "temperature");
                                    intent.putExtra("value", t);
                                    startActivity(intent);
                                    break;
                                } else if (l > 300) {
                                    intent.putExtra("notification", 4);
                                    intent.putExtra("reason", "inflammable gas");
                                    intent.putExtra("value", l);
                                    startActivity(intent);
                                    break;
                                } else if (c > 300) {
                                    intent.putExtra("notification", 5);
                                    intent.putExtra("reason", "carbon gas");
                                    intent.putExtra("value", l);
                                    startActivity(intent);
                                    break;
                                }

                            }
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }


}





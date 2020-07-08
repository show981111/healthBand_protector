package com.hanium.healthband;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    String getData;
    TextView textView;
    Button bt_connect;
    Button bt_disConnect;
    TextView tv_hanium;
    int data = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_hanium = findViewById(R.id.tv_receivedData);
        bt_connect = findViewById(R.id.bt_connect);
        bt_disConnect = findViewById(R.id.bt_cancel);
        tv_hanium.setText("hanium Project");

        final Timer t = new Timer();

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //connect Arduino and get Data
                getData = "";


                //1초간격으로 데이터를 보냄
                TimerTask myTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Log.d("resGetting","gogo");
                        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
                        Connect connect = new Connect(handler, tv_hanium);
                        connect.sendData(data,"http://show981111.cafe24.com/testUpdata.php");
                        data++;
                    }
                };
                t.scheduleAtFixedRate(myTimerTask, 0, 1000);

            }
        });

        bt_disConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.cancel();
            }
        });


    }



}

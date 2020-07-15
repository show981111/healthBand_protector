package com.hanium.healthband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import okhttp3.FormBody;
import okhttp3.RequestBody;

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
        Button bt_goToLogin = findViewById(R.id.bt_goToLogin);
        tv_hanium.setText("hanium Project");
        final Button bt_send = findViewById(R.id.bt_send);

        final Timer t = new Timer();
        final ConnectTcp connectTcp = new ConnectTcp();

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //connect Arduino and get Data
                //여기서 블루투스 아두이노 연결해서 1초 간격으로 데이터들을 받아오면은...
                getData = "";


                //여기서 1초간격으로 데이터를 서버로 보냄
                //http 통신
//                TimerTask myTimerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        ConnectHttp connect = new ConnectHttp(MainActivity.this);
//                        connect.postData(data,"http://show981111.cafe24.com/testUpdata.php", tv_hanium);
//                        data++;
//                    }
//                };
//                t.scheduleAtFixedRate(myTimerTask, 0, 1000);//1초 간격 세팅신
                //tcp통신
                connectTcp.sendMessage("receivers");

            }
        });

        bt_disConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    connectTcp.closeSocket();
                } catch (IOException e) {
                    Log.d("tcp", "fail to close ");
                    e.printStackTrace();
                }
            }
        });//데이터 전송 중단
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    connectTcp.sendAdditionalMessage("send data from bt_send");
                    //connectTcp.sendDataEverySecond();

            }
        });

        //login 창으로 이동
        bt_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectTcp.sendAdditionalMessage("send data from bt_goToLogin");
                //connectTcp.sendAdditionalMessage("james : 150");
//                Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
//                MainActivity.this.startActivity(goToLogin);
            }
        });


    }



}

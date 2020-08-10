package com.hanium.healthband;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {


    public static final String EXTRAS_DEVICE_ADDRESS = "address";
    public static String EXTRAS_DEVICE_NAME;

    String getData;
    TextView textView;
    Button bt_connect;
    Button bt_disConnect;
    TextView tv_hanium;
    int data = 10;
    Timer timer;
    private BluetoothAdapter bluetoothAdapter;




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

                getData = "";
                //connectTcp.sendMessage("receivers");

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
//                    connectTcp.sendAdditionalMessage("send data from bt_send");
//                    connectTcp.sendDataEverySecond();
                Log.d("SCAN_BLE", "start");
                Log.d("BLUE", "Start");
                Intent startConnect = new Intent(MainActivity.this, DeviceScanActivity.class);
                //DeviceScanActivity.class.getLayoutInflater();
                MainActivity.this.startActivity(startConnect);
            }
        });

        //login 창으로 이동
        bt_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                connectTcp.sendAdditionalMessage("send data from bt_goToLogin");
//                connectTcp.sendAdditionalMessage("james : 150");
                Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(goToLogin);

            }
        });


        //blueTooth**************************************************************
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i("info", "No fine location permissions");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }


    }
}

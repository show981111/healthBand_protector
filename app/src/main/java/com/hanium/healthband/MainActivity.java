package com.hanium.healthband;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.core.BluetoothGattChannel;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRAS_DEVICE_ADDRESS = "address";
    public static String EXTRAS_DEVICE_NAME;

    String getData;
    TextView textView;
    Button bt_connect;
    Button bt_disConnect;
    TextView tv_hanium;
    int data = 10;


    public static final String TAG = "BLE1_ACTIVITY";


    // Initializes Bluetooth adapter.
//    final BluetoothManager bluetoothManager =
//            (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//
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
                    connectTcp.sendDataEverySecond();
                Log.d("SCAN_BLE", "start");
//                DeviceScanActivity.class.getLayoutInflater();
                Log.d("BLUE", "Start");
//                Intent startConnect = new Intent(MainActivity.this, DeviceScanActivity.class);
//                MainActivity.this.startActivity(startConnect);
            }
        });

        //login 창으로 이동
        bt_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectTcp.sendAdditionalMessage("send data from bt_goToLogin");
                connectTcp.sendAdditionalMessage("james : 150");
//                Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
//                MainActivity.this.startActivity(goToLogin);
                Log.d("SCAN_BLE", "start");

            }
        });


        //blueTooth**************************************************************

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }


    }

}

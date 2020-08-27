package com.hanium.healthband_protector;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanium.healthband_protector.model.User;
import com.hanium.healthband_protector.recyclerView.wearerListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;


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

    public static ArrayList<User> linkedUserArrayList = new ArrayList<>();
    public static User user;
    private RecyclerView rv_wearerList;
    private wearerListAdapter wearerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_hanium = findViewById(R.id.tv_receivedData);
        bt_connect = findViewById(R.id.bt_connect);
        bt_disConnect = findViewById(R.id.bt_cancel);
        Button bt_goToLogin = findViewById(R.id.bt_goToLogin);
        tv_hanium.setText("hanium Project");
        rv_wearerList = findViewById(R.id.rv_wearerList);
        final Button bt_send = findViewById(R.id.bt_send);

        final Timer t = new Timer();
        final ConnectTcp connectTcp = new ConnectTcp();


        Intent getIntent = getIntent();
        if(getIntent != null){
            linkedUserArrayList = getIntent.getParcelableArrayListExtra("LinkedUserList");
            user = getIntent.getParcelableExtra("userData");
            if(linkedUserArrayList != null) {
                for (int i = 0; i < linkedUserArrayList.size(); i++) {
                    Log.d("main", linkedUserArrayList.get(i).getName());
                }
                wearerListAdapter = new wearerListAdapter(MainActivity.this, linkedUserArrayList);
                rv_wearerList.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false));
                rv_wearerList.setAdapter(wearerListAdapter);
            }
        }


        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData = "";
                //connectTcp.sendMessage("sender,guar1,guar2,guar3,guar4");
                Intent goToChart = new Intent(MainActivity.this, ChartActivity.class);
                MainActivity.this.startActivity(goToChart);

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
                    //connectTcp.sendAdditionalMessage("10,122,123,-123,-132,-123,312,321");
                    //connectTcp.sendDataEverySecond();
//                Log.d("SCAN_BLE", "start");
//                Log.d("BLUE", "Start");
//                Intent startConnect = new Intent(MainActivity.this, DeviceControlActivity.class);
//                //DeviceScanActivity.class.getLayoutInflater();
//                MainActivity.this.startActivity(startConnect);

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Title")
                        .setMessage("Message")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String editTextInput = input.getText().toString();
                                Log.d("onclick","editext value is: "+ editTextInput);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
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





    }
}

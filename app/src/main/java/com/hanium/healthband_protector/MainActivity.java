package com.hanium.healthband_protector;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hanium.healthband_protector.model.LinkedInfo;
import com.hanium.healthband_protector.model.Message;
import com.hanium.healthband_protector.model.User;
import com.hanium.healthband_protector.recyclerView.wearerListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Timer;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


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

    private static ArrayList<User> linkedUserArrayList = new ArrayList<>();
    private static User user = new User("a", "a", "a", "a");
    private RecyclerView rv_wearerList;
    private wearerListAdapter wearerListAdapter;
    private String token ;

    private EditText et_message;
    private Button bt_send;

    public String TAG = "data from server ******";

    private Socket mSocket;
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
        bt_send = findViewById(R.id.bt_send);

        final Timer t = new Timer();


        Intent getIntent = getIntent();
        if(getIntent != null){
            linkedUserArrayList = getIntent.getParcelableArrayListExtra("LinkedUserList");
            user = getIntent.getParcelableExtra("userData");
            token = getIntent.getStringExtra("token");
            if(linkedUserArrayList != null) {
                for (int i = 0; i < linkedUserArrayList.size(); i++) {
                    Log.d("main", linkedUserArrayList.get(i).getName());
                }
                wearerListAdapter = new wearerListAdapter(MainActivity.this, linkedUserArrayList, token);
                rv_wearerList.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false));
                rv_wearerList.setAdapter(wearerListAdapter);
            }
        }



        if(linkedUserArrayList.size() > 0){
            bt_goToLogin.setVisibility(View.GONE);
        }

//        user = new User("p3@gmail.com", "p3@gmail.com", "11111", "P");
//        linkedUserArrayList = new ArrayList<>();
//        linkedUserArrayList.add(new User("w3@gmail.com", "w3@gmail.com", "11111", "W"));
//        linkedUserArrayList.add(new User("w2@gmail.com", "w2@gmail.com", "11111", "W"));
//        linkedUserArrayList.add(new User("test@test.com", "test@test.com", "11111", "W"));


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
//                try {
//                    connectTcp.closeSocket();
//                } catch (IOException e) {
//                    Log.d("tcp", "fail to close ");
//                    e.printStackTrace();
//                }

//                Intent goToChart = new Intent(MainActivity.this, EnvChartActivity.class);
//                MainActivity.this.startActivity(goToChart);
            }
        });//데이터 전송 중단

//        bt_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    //connectTcp.sendAdditionalMessage("10,122,123,-123,-132,-123,312,321");
//                    //connectTcp.sendDataEverySecond();
////                Log.d("SCAN_BLE", "start");
////                Log.d("BLUE", "Start");
////                Intent startConnect = new Intent(MainActivity.this, DeviceControlActivity.class);
////                //DeviceScanActivity.class.getLayoutInflater();
////                MainActivity.this.startActivity(startConnect);
//
//                final EditText input = new EditText(MainActivity.this);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT);
//                input.setLayoutParams(lp);
//
//                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Title")
//                        .setMessage("Message")
//                        .setView(input)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                String editTextInput = input.getText().toString();
//                                Log.d("onclick","editext value is: "+ editTextInput);
//                            }
//                        })
//                        .setNegativeButton("Cancel", null)
//                        .create();
//                dialog.show();
//            }
//        });

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

//        String SERVER_PATH = "http://52.79.230.118:8000";
//
//        final mySocket mySocket = new mySocket(SERVER_PATH,user, linkedUserArrayList);
//        mySocket.connectToServer();


        //*************SOCKET

//        String SERVER_PATH = "http://52.79.230.118:8000";
        et_message = findViewById(R.id.et_message);
//
//        try {
//            mSocket = IO.socket(SERVER_PATH);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        mSocket.connect();
//
//        Gson gson = new Gson();
//        try {
//            LinkedInfo msg = new LinkedInfo(user.getUser_type() ,user.getUsername(), linkedUserArrayList);
//            JSONObject obj = new JSONObject(gson.toJson(msg));
//            mSocket.emit("joinLink", obj);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
////        for(int i = 0; i< linkedUserArrayList.size(); i++){
////
////            mSocket.emit("joinLink", user.getUser_type()+"," +user.getUsername() + "," +linkedUserArrayList.get(i).getUsername());
////        }
//
//
//        // 이제 연결이 성공적으로 되게 되면, server측에서 "connect" event 를 발생시키고
//        // 아래코드가 그 event 를 핸들링 합니다. onConnect는 65번째 줄로 가서 살펴 보도록 합니다.
//        mSocket.on(Socket.EVENT_CONNECT, onConnect);
//        //mSocket.on("welcome", receiveMessage);
//
//        mSocket.on("welcome", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                try {
//                    JSONObject messageJson = new JSONObject(args[0].toString());
//                    String receivedData = messageJson.getString("text");
//                    Log.w(TAG, "received Data from socekt" + messageJson);
//                    Log.w(TAG, "received Data from socekt" + receivedData);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        mSocket.on("sendData", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                try {
//                    JSONObject messageJson = new JSONObject(args[0].toString());
//                    String receivedData = messageJson.getString("text");
//                    Log.w(TAG, "received Data from socekt" + messageJson);
//                    Log.w(TAG, "received Data from socekt" + receivedData);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        //*************SOCKET

        //mSocket.on("logout", onLogout);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                MainActivity.this.startActivity(intent);
                String content = et_message.getText().toString();
//                Gson gson = new Gson();
//                try {
//                    Message msg = new Message(user.getUsername(), content);
//                    JSONObject obj = new JSONObject(gson.toJson(msg));
//                    mSocket.emit("androidMessage", obj);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                //mySocket.sendDataToServer(content);


                //Log.d(TAG, "Socket is connected with " +  user.getUsername());
            }
        });


    }

//    Emitter.Listener onConnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            if(mSocket == null){
//                Log.w(TAG, "null!!!");
//            }else{
//                mSocket.emit("login", user.getUsername());
//            }
//            Log.d(TAG, "Socket is connected with " +  user.getUsername());
//        }
//    };





}

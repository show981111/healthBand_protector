package com.hanium.healthband_protector;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.hanium.healthband_protector.model.LinkedInfo;
import com.hanium.healthband_protector.model.Message;
import com.hanium.healthband_protector.model.User;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class mySocket {

    private static final String TAG = "mySocket";
    private String url;
    private Socket mSocket;
    private User user;
    private ArrayList<User> linkedUserArrayList = new ArrayList<>();

    public mySocket(String url, User user, ArrayList<User> linkedUserArrayList) {
        this.url = url;
        this.user = user;
        this.linkedUserArrayList = linkedUserArrayList;
    }

    public void connectToServer(){
        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.connect();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        joinLink();
//        waitEvent();
    }

    private void joinLink(){
        LinkedInfo msg = new LinkedInfo(user.getUser_type() ,user.getUsername(), linkedUserArrayList);
        Gson gson = new Gson();
        try {
            JSONObject obj = new JSONObject(gson.toJson(msg));
            mSocket.emit("joinLink", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void waitSensorData(){
        //mSocket.on("welcome", receiveMessage);

        mSocket.on("welcome", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject messageJson = new JSONObject(args[0].toString());
                    String receivedData = messageJson.getString("text");
                    Log.w(TAG, "received Data from socekt" + messageJson);
                    Log.w(TAG, "received Data from socekt" + receivedData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mSocket.on("sendData", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject messageJson = new JSONObject(args[0].toString());
                    String receivedData = messageJson.getString("text");

                    Log.w(TAG, "received Data from socekt" + messageJson);
                    Log.w(TAG, "received Data from socekt" + receivedData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getLocation(final Activity activity, final NaverMap naverMap, final LocationOverlay locationOverlay){
        mSocket.on("sendLocation", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject messageJson = new JSONObject(args[0].toString());
                    String receivedData = messageJson.getString("text");
                    Log.w(TAG, "received Data from socekt" + messageJson);
                    final double lang = messageJson.getDouble("lang");
                    final double lat = messageJson.getDouble("lat");


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            locationOverlay.setPosition(new LatLng(lat, lang));
                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lang));
                            naverMap.moveCamera(cameraUpdate);

                        }
                    });
                    Log.w(TAG, "received Data from socekt" + receivedData + " " + lat + " " + lang);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void sendDataToServer(String content){
        Gson gson = new Gson();
        try {
            Message msg = new Message(user.getUsername(), content);
            JSONObject obj = new JSONObject(gson.toJson(msg));
            mSocket.emit("androidMessage", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(mSocket == null){
                Log.w(TAG, "null!!!");
            }else{
                mSocket.emit("login", user.getUsername());
            }
            Log.d(TAG, "Socket is connected with " +  user.getUsername());
        }
    };

    public void disconnect(){
        mSocket.disconnect();
    }


}

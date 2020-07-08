package com.hanium.healthband;

import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect {//connect to server class

    private android.os.Handler handler ;
    private TextView tv_sendData ;
    int i = 0;
    public Connect(android.os.Handler handler, TextView tv_sendData) {
        this.handler = handler;
        this.tv_sendData = tv_sendData;
    }


    void sendData(int data, String url){//send Data

        try {
            Log.d("resGetting","start");
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("data", String.valueOf(data))
                    .build();

//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(formBody)
//                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            //비동기 처리 (enqueue 사용)
            client.newCall(request).enqueue(new Callback() {
                //비동기 처리를 위해 Callback 구현
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error + Connect Server Error is " + e.toString());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful())
                    {
                        if(response.body() != null) {
                            final String res = response.body().string();
                            Log.d("resGetting",res+ "YES!");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    i++;
                                    Log.d("resGetting","..."+i);
                                    tv_sendData.setText(res);
                                    //handler.postDelayed(this, 1000);
                                }
                            });
                        }
                    }


                }
            });

        } catch (Exception e){
            System.err.println(e.toString());
        }
    }


}

package com.hanium.healthband;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectHttp<T extends View> {//connect to server class

    private Context context;
    private android.os.Handler handler;// 서버와의 연결 후에 서버에서 보내주는 데이터를 토데로 UI를 업데이트 하기 위해서 핸들러 준비
    private int i = 0;
    private T view;
    public ConnectHttp(Context context) {
        this.handler = new android.os.Handler(Looper.getMainLooper());//핸들러 초기화
        this.context = context;
    }

    void postData(int data ,String url, T t){//send Data
        view = t;
        try {
            OkHttpClient client = new OkHttpClient();//http 연결 라이브러리로 okhttp 라이브러리 사용

            RequestBody formBody = new FormBody.Builder()//요청 빌드하고
                    .add("data", String.valueOf(data))
                    .build();

            Request request = new Request.Builder()//전송
                    .url(url)
                    .post(formBody)
                    .build();

            //비동기 처리 (enqueue 사용)
            client.newCall(request).enqueue(new Callback() {
                //비동기 처리를 위해 Callback 구현
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {//실패시
                    Log.d("postData",e.toString()+ "?!");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful())//성공시
                    {
                        if(response.body() != null) {//get response
                            final String res = response.body().string();//서버에서 날려준 데이터
                            Log.d("postData",res+ "?!");

                            if(handler != null) {//update UI
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        i++;
                                        Log.d("postData", "..." + i);
                                        if(view instanceof TextView)
                                        {
                                            TextView tv = (TextView) view;
                                            tv.setText(res);//그 데이터로 텍스트 뷰 업데이트
                                        }
                                        //handler.postDelayed(this, 1000);
                                    }
                                });
                            }else{
                                //do not have to update UI
                            }
                        }
                    }


                }
            });

        } catch (Exception e){
            System.err.println(e.toString());
        }
    }



}

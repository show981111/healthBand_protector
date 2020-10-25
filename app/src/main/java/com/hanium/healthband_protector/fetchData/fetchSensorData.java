package com.hanium.healthband_protector.fetchData;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.hanium.healthband_protector.model.SensorData;
import com.hanium.healthband_protector.model.Stat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class fetchSensorData extends AsyncTask<String, Void, SensorData > {

    private String token;
    private String wearerID;
    private String TAG = "fetchSensorData";
    private TextView tv_temp;
    private TextView tv_humid;
    private TextView tv_heartRate;
    private TextView tv_meter;

    public fetchSensorData(String token, String wearerID, TextView tv_temp, TextView tv_humid, TextView tv_heartRate, TextView tv_meter) {
        this.token = token;
        this.wearerID = wearerID;
        this.tv_temp = tv_temp;
        this.tv_humid = tv_humid;
        this.tv_heartRate = tv_heartRate;
        this.tv_meter = tv_meter;
    }

    @Override
    protected SensorData doInBackground(String... strings) {
        String url = strings[0];

        if(TextUtils.isEmpty(token)&& TextUtils.isEmpty(wearerID) ){
            return null;
        }
        OkHttpClient okHttpClient = new OkHttpClient();

        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("wearerID", wearerID);

        Request request = new Request.Builder()
                .header("Authorization", "Token " + token)
                .url(httpBuilder.build())
                .build();
        Log.d(TAG, httpBuilder.build().toString());

        Response response;
        try {
            response = okHttpClient.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
            return null;
        }
        String jsonData;

        try {

            if(response.code() == 200) {

                jsonData = response.body().string();
                Log.d(TAG, jsonData);
                JSONObject responseObject = new JSONObject(jsonData);
                Iterator<String> iter = responseObject.keys(); //This should be the iterator you want.
                while(iter.hasNext()){
                    String key = iter.next();
                    Log.d(TAG, key);
                    if(key.equals("data")){
                        JSONObject object = responseObject.getJSONObject(key);
                        String heartRate = object.getString("heartRate");
                        String humid = object.getString("humid");
                        String meter = object.getString("meter");
                        String nowDate = object.getString("nowDate");
                        String nowTime = object.getString("nowTime");
                        String temp = object.getString("temp");
                        //String temperature, String humidity, String heartRate, String meter
                        SensorData sensorData = new SensorData(temp, humid, heartRate, meter);
                        return  sensorData;
                    }


                }

                return null;
            }else{
                return null;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(SensorData sensorData) {
        super.onPostExecute(sensorData);
        if(sensorData != null){
            tv_heartRate.setText(sensorData.getHeartRate());
            tv_temp.setText(sensorData.getTemperature());
            tv_humid.setText(sensorData.getHumidity());
            //tv_meter.setText(sensorData.getMeter());
        }
    }
}

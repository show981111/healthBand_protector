package com.hanium.healthband_protector.fetchData;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hanium.healthband_protector.MapActivity;
import com.hanium.healthband_protector.model.LocationData;
import com.hanium.healthband_protector.model.User;
import com.hanium.healthband_protector.recyclerView.wearerListAdapter;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.LocationOverlay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class fetchWearerLocation extends AsyncTask<String, Void, LocationData> {

    private String token;
    private Activity activity;
    private NaverMap naverMap;
    private LocationOverlay locationOverlay;
    private String wearerID;
    private String TAG = "FETCH";

    public fetchWearerLocation(Activity activity, String token ,String wearerID, NaverMap naverMap, LocationOverlay locationOverlay) {
        this.activity = activity;
        this.wearerID = wearerID;
        this.naverMap = naverMap;
        this.token = token;
        this.locationOverlay = locationOverlay;
    }

    @Override
    protected LocationData doInBackground(String... strings) {
        String url = strings[0];

        if(TextUtils.isEmpty(wearerID)){
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

        try {
            Response response = okHttpClient.newCall(request).execute();

            if(response.body() == null) return null;

            String jsonData = response.body().string();
            JSONObject responseObject = new JSONObject(jsonData);
            if(responseObject.getString("status").equals("success")) {
                JSONObject locationDataObject = responseObject.getJSONObject("data");
                String lat = locationDataObject.getString("latitude");
                String lng = locationDataObject.getString("longitude");
                LocationData locationData = new LocationData(lat, lng);
                return locationData;
            }


            return null;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(LocationData loc) {
        super.onPostExecute(loc);

        if(loc == null) return;

        double lat = Double.parseDouble(loc.getLat());
        double lng = Double.parseDouble(loc.getLng());
        Log.w(TAG, lat + " " + lng);
        locationOverlay.setPosition(new LatLng(lat,lng));
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lng));
        naverMap.moveCamera(cameraUpdate);
    }
}

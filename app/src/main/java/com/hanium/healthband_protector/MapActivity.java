package com.hanium.healthband_protector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.hanium.healthband_protector.model.User;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private mySocket mySocket;
    private LocationSource locationSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        NaverMapSdk.getInstance(this).setClient(
//                new NaverMapSdk.NaverCloudPlatformClient("w6rptd24pj"));

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        String SERVER_PATH = "http://52.79.230.118:8000";
        User user = new User("p1@gmail.com","p1@gmail.com","p1@gmail.com", "P" );
        ArrayList<User> linkedUserArrayList = new ArrayList<>();
        linkedUserArrayList.add(new User("w3@gmail.com", "w3@gmail.com", "11111", "W"));
        linkedUserArrayList.add(new User("w1@gmail.com", "w2@gmail.com", "11111", "W"));
        linkedUserArrayList.add(new User("test@test.com", "test@test.com", "11111", "W"));
        mySocket = new mySocket(SERVER_PATH, user,linkedUserArrayList);
        mySocket.connectToServer();



    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);


//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(38, -122));
//        marker.setMap(naverMap);
        mySocket.getLocation(MapActivity.this, naverMap, locationOverlay);
//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(37.5670135, 126.9783740));
//        marker.setMap(naverMap);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

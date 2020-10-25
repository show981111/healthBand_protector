package com.hanium.healthband_protector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanium.healthband_protector.Api.API;
import com.hanium.healthband_protector.fetchData.fetchSensorData;

import java.util.Timer;
import java.util.TimerTask;

public class wearerInfoActivity extends AppCompatActivity {

    private TextView tv_wearerName;
    private String wearerID;
    private String token;
    private TextView tv_heartRate;
    private TextView tv_temp;
    private TextView tv_humid;
    private TextView tv_meter;

    private TimerTask tt;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearer_info);
        tv_wearerName = findViewById(R.id.tv_wearerNameInfo);

        tv_heartRate = findViewById(R.id.tv_heartRate);
        tv_temp = findViewById(R.id.tv_temperature);
        tv_humid = findViewById(R.id.tv_humidity);
        tv_meter = findViewById(R.id.tv_meter);

        tt = new TimerTask() {
            @Override
            public void run() {
                Log.w("FETCH", "FETCH SENSOR DATA CALLED");
                fetchSensorData fetchSensorData = new fetchSensorData(token, wearerID, tv_temp, tv_humid, tv_heartRate, tv_meter);
                fetchSensorData.execute(API.RealTimeSensorData);
            }
        };

        timer = new Timer();
        timer.schedule(tt, 0, 3000);

        Intent getIntent = getIntent();
        if(getIntent != null) {
            tv_wearerName.setText(getIntent.getStringExtra("userName"));
            wearerID = getIntent.getStringExtra("userID");
            token = getIntent.getStringExtra("token");

        }

        RelativeLayout rl_heart = findViewById(R.id.rl_heart);
        RelativeLayout rl_env = findViewById(R.id.rl_tempHumid);
        RelativeLayout rl_sound = findViewById(R.id.rl_sound);
        rl_sound.setVisibility(View.GONE);
        Button bt_viewLocation = findViewById(R.id.bt_viewLocation);

        rl_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wearerInfoActivity.this, ChartActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("userID", wearerID);
                intent.putExtra("userName", tv_wearerName.getText().toString());
                intent.putExtra("sensorType", "heartRate");

                wearerInfoActivity.this.startActivity(intent);
            }
        });

//        rl_sound.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(wearerInfoActivity.this, ChartActivity.class);
//                intent.putExtra("token", token);
//                intent.putExtra("userID", wearerID);
//                intent.putExtra("userName", tv_wearerName.getText().toString());
//                intent.putExtra("sensorType", "sound");
//
//                wearerInfoActivity.this.startActivity(intent);
//            }
//        });

        rl_env.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wearerInfoActivity.this, EnvChartActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("userID", wearerID);
                intent.putExtra("userName", tv_wearerName.getText().toString());
                wearerInfoActivity.this.startActivity(intent);
            }
        });

        bt_viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wearerInfoActivity.this, MapActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("wearerID", wearerID);
                intent.putExtra("userName", tv_wearerName.getText().toString());
                wearerInfoActivity.this.startActivity(intent);
                Log.w("FETCHLOC", "wearerInfo "+wearerID + " " + token);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        tt.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //timer.schedule(tt, 0, 3000);
    }
}

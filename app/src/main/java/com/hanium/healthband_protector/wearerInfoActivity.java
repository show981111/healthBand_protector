package com.hanium.healthband_protector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class wearerInfoActivity extends AppCompatActivity {

    private TextView tv_wearerName;
    private String wearerID;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearer_info);
        tv_wearerName = findViewById(R.id.tv_wearerNameInfo);


        Intent getIntent = getIntent();
        if(getIntent != null) {
            tv_wearerName.setText(getIntent.getStringExtra("userName"));
            wearerID = getIntent.getStringExtra("userID");
            token = getIntent.getStringExtra("token");
        }

        RelativeLayout rl_heart = findViewById(R.id.rl_heart);
        RelativeLayout rl_env = findViewById(R.id.rl_tempHumi);
        RelativeLayout rl_sound = findViewById(R.id.rl_sound);

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

        rl_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wearerInfoActivity.this, ChartActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("userID", wearerID);
                intent.putExtra("userName", tv_wearerName.getText().toString());
                intent.putExtra("sensorType", "sound");

                wearerInfoActivity.this.startActivity(intent);
            }
        });

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


    }
}

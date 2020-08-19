package com.hanium.healthband_protector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class wearerInfoActivity extends AppCompatActivity {

    private TextView tv_wearerName;
    private String wearerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearer_info);
        tv_wearerName = findViewById(R.id.tv_wearerNameInfo);

        Intent getIntent = getIntent();
        if(getIntent != null) {
            tv_wearerName.setText(getIntent.getStringExtra("userName"));
            wearerID = getIntent.getStringExtra("userID");
        }
    }
}

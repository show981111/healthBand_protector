package com.hanium.healthband;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText et_userName = findViewById(R.id.et_registerName);
        EditText et_userEmail = findViewById(R.id.et_registerEmail);
        EditText et_userPhone = findViewById(R.id.et_registerPhone);
        EditText et_userPassword = findViewById(R.id.et_registerPassword);
        EditText et_userPasswordCheck = findViewById(R.id.et_registerPasswordCheck);
        RadioGroup rg_userType = findViewById(R.id.rg_registerType);

        Button bt_register = findViewById(R.id.bt_registerSend);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send user information to server
            }
        });

    }
}

package com.hanium.healthband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText et_loginEmail = findViewById(R.id.et_loginEmail);
        EditText et_loginPW = findViewById(R.id.et_loginPassword);
        Button bt_loginWithID = findViewById(R.id.bt_login);
        Button bt_loginWithBluetooth = findViewById(R.id.bt_loginBluetooth);
        Button bt_register = findViewById(R.id.bt_loginRegister);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(goToRegister);
            }
        });
    }
}

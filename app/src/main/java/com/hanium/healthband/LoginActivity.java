package com.hanium.healthband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hanium.healthband.model.User;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText et_loginEmail = findViewById(R.id.et_loginEmail);
        final EditText et_loginPW = findViewById(R.id.et_loginPassword);
        Button bt_loginWithID = findViewById(R.id.bt_login);
        Button bt_loginWithBluetooth = findViewById(R.id.bt_loginBluetooth);
        Button bt_register = findViewById(R.id.bt_loginRegister);

        bt_loginWithID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_loginEmail.getText().toString();
                String userPW = et_loginPW.getText().toString();
                LoginTask loginTask = new LoginTask(userID, userPW);
                loginTask.execute("http://ec2-3-34-84-225.ap-northeast-2.compute.amazonaws.com:8000/rest-auth/login/");

            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(goToRegister);
            }
        });
    }

    class LoginTask extends AsyncTask<String, Void, User>{

        private String userID;
        private String userPW;

        public LoginTask(String userID, String userPW) {
            this.userID = userID;
            this.userPW = userPW;
        }

        @Override
        protected User doInBackground(String... strings) {
            String url = strings[0];

            if(TextUtils.isEmpty(userID)){
                return null;
            }
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("username", userID)
                    .add("email", "")
                    .add("password", userPW)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                Log.d("loginTask", response.body().string());
                Gson gson = new Gson();
                User user = gson.fromJson(response.body().charStream(), User.class);

                return user;

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("loginTask", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if(user == null) return;
            Log.d("loginTask", user.name);
        }
    }
}

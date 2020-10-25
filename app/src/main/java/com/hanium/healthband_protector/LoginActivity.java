package com.hanium.healthband_protector;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hanium.healthband_protector.Api.API;
import com.hanium.healthband_protector.postData.postToken;
import com.hanium.healthband_protector.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private String userID = null;
    private String firebaseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText et_loginEmail = findViewById(R.id.et_loginEmail);
        final EditText et_loginPW = findViewById(R.id.et_loginPassword);
        Button bt_loginWithID = findViewById(R.id.bt_login);
        Button bt_loginWithBluetooth = findViewById(R.id.bt_loginBluetooth);
        Button bt_register = findViewById(R.id.bt_loginRegister);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( LoginActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                firebaseToken = instanceIdResult.getToken();
                Log.d("asdf", firebaseToken);
            }
        });

        bt_loginWithID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_loginEmail.getText().toString();
                String userPW = et_loginPW.getText().toString();
                LoginTask loginTask = new LoginTask(userID, userPW);
                loginTask.execute("http://ec2-3-34-84-225.ap-northeast-2.compute.amazonaws.com:8000/custom/login/");
                //loginTask.execute("http://52.79.230.118:8000/user/login");
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
        private ArrayList<User> linkedUserArrayList = new ArrayList<>();
        private String token;

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
                    .add("password", userPW)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("loginTask", e.getMessage());
                return null;
            }

            String jsonData = null;
            if(response != null ){
                try {
                    jsonData = response.body().string();
//                    JSONArray responseArray = new JSONArray(jsonData);
                    //JSONObject responseObject = responseArray.getJSONObject(0);
                    JSONObject responseObject = new JSONObject(jsonData);
                    Log.d("loginTask", jsonData);
                    if(responseObject.getString("status").equals("success")) {
                        token = responseObject.getString("key");
                        JSONObject userDataObject = responseObject.getJSONObject("userdata");

                        String username = userDataObject.getString("username");
                        String name = userDataObject.getString("name");
                        String user_type = userDataObject.getString("user_type");
                        String phone_number = userDataObject.getString("phone_number");

//                        String username = responseObject.getString("username");
//                        String name = responseObject.getString("name");
//                        String user_type = responseObject.getString("user_type");
//                        String phone_number = responseObject.getString("phone_number");

                        JSONObject linkedUserListObj = responseObject.getJSONObject("linked_users");
                        Iterator<String> iter = linkedUserListObj.keys(); //This should be the iterator you want.
                        while(iter.hasNext()){
                            String key = iter.next();
                            Log.d("loginTask", key);
                            JSONObject linkedUserData = linkedUserListObj.getJSONObject(key);
                            String linked_userID = linkedUserData.getString("username");
                            String linked_username = linkedUserData.getString("name");
                            String linked_phone_number = linkedUserData.getString("phone_number");
                            String linked_user_type;
                            if(user_type.equals("P")){
                                linked_user_type = "W";
                            }else{
                                linked_user_type = "P";
                            }
                            User linkedUser = new User(linked_userID,linked_username,linked_phone_number,linked_user_type);
                            linkedUserArrayList.add(linkedUser);
                        }

                        User user = new User(username, name, phone_number, user_type);
                        return user;
                    }else{
                        return null;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }else{
                return null;
            }


        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            //Log.d("Login", user.getName() + user.getUser_type() + user.getPhone_number());
            if(user == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("아이디 비밀번호를 다시 확인해주세요!")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();
            }else{
                if(user.getUser_type().equals("P")) {
                    Log.w("FCMTOKEN" , firebaseToken);
                    postToken postToken = new postToken(token, firebaseToken, user.getUsername());
                    postToken.execute(API.postFCM);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("LinkedUserList", linkedUserArrayList);
                    intent.putExtra("userData", user);
                    intent.putExtra("token", token);
                    LoginActivity.this.startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("착용자 어플을 다운받아 주세요!")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();
                }
                Log.d("loginTask", user.getName());
            }
        }
    }
}

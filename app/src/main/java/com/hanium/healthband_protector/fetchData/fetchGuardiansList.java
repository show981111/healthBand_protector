package com.hanium.healthband.fetchData;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hanium.healthband.model.User;
import com.hanium.healthband.recyclerView.guardiansListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class fetchGuardiansList extends AsyncTask<String, Void, User[]> {

    private Context mContext;
    private String userID;
    private guardiansListAdapter guardiansListAdapter;
    private ArrayList<User> userArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    public fetchGuardiansList(Context c, String userID, RecyclerView recyclerView) {
        mContext = c;
        this.userID = userID;
        this.recyclerView= recyclerView;
    }

    @Override
    protected User[] doInBackground(String... strings) {
        String url = strings[0];

        if(TextUtils.isEmpty(userID)){
            return null;
        }
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userID", userID)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();

            if(response.body() == null) return null;

            Log.d("fetchGuardiansList", response.body().string());
            Gson gson = new Gson();
            User[] Users = gson.fromJson(response.body().charStream(), User[].class);
            return Users;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fetchGuardiansList", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(User[] users) {
        super.onPostExecute(users);
        if(users != null){
            for(User user1 : users){
                userArrayList.add(user1);
            }
            guardiansListAdapter = new guardiansListAdapter(mContext,userArrayList);

            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(guardiansListAdapter);
        }
    }
}

package com.hanium.healthband_protector.fetchData;

import android.os.AsyncTask;

import com.hanium.healthband_protector.model.Stat;

public class fetchStatList extends AsyncTask<String, Void, Stat[]> {

    //센서 데이터 종류, userID post
    //get
    @Override
    protected Stat[] doInBackground(String... strings) {
        return new Stat[0];
    }
}

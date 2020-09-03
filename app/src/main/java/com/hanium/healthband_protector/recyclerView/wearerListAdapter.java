package com.hanium.healthband_protector.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanium.healthband_protector.R;
import com.hanium.healthband_protector.model.User;
import com.hanium.healthband_protector.wearerInfoActivity;

import java.util.ArrayList;

public class wearerListAdapter extends RecyclerView.Adapter<wearerViewHolder> {

    private Context mContext;
    private ArrayList<User> wearerList = new ArrayList<>();
    private final String token;

    public wearerListAdapter(Context mContext, ArrayList<User> wearerList, String token) {
        this.mContext = mContext;
        this.wearerList = wearerList;
        this.token = token;
    }

    @NonNull
    @Override
    public wearerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.list_item_wearer,null);
        wearerViewHolder wearerViewHolder = new wearerViewHolder(baseView);
        return wearerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull wearerViewHolder holder, final int position) {
        holder.wearerName.setText(wearerList.get(position).getName());
        holder.wearerPhone.setText(wearerList.get(position).getPhone_number());

        holder.ll_wearerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, wearerInfoActivity.class);
                intent.putExtra("userName",wearerList.get(position).getName());
                intent.putExtra("userID",wearerList.get(position).getUsername());
                intent.putExtra("token", token);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return wearerList.size();
    }
}

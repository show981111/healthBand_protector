package com.hanium.healthband.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanium.healthband.R;
import com.hanium.healthband.model.User;

import java.util.ArrayList;

public class guardiansListAdapter extends RecyclerView.Adapter<guardiansViewHolder> {

    private Context mContext;
    private ArrayList<User> guardianList = new ArrayList<>();

    public guardiansListAdapter(Context mContext, ArrayList<User> guardianList) {
        this.mContext = mContext;
        this.guardianList = guardianList;
    }

    @NonNull
    @Override
    public guardiansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.list_item_guardian,null);
        guardiansViewHolder guardiansViewHolder = new guardiansViewHolder(baseView);
        return guardiansViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull guardiansViewHolder holder, int position) {
        holder.guardianName.setText(guardianList.get(position).getName());
        holder.guardianPhone.setText(guardianList.get(position).getPhone_number());
    }

    @Override
    public int getItemCount() {
        return guardianList.size();
    }
}

package com.hanium.healthband_protector.recyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanium.healthband_protector.R;

public class wearerViewHolder extends RecyclerView.ViewHolder {

    public TextView wearerName;
    public TextView wearerPhone;
    public LinearLayout ll_wearerItem;

    public wearerViewHolder(@NonNull View itemView) {
        super(itemView);

        wearerName = itemView.findViewById(R.id.tv_wearerName);
        wearerPhone = itemView.findViewById(R.id.tv_wearerPhone);
        ll_wearerItem = itemView.findViewById(R.id.ll_wearerListItem);
    }
}

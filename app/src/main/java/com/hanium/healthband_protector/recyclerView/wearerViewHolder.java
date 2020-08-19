package com.hanium.healthband.recyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanium.healthband.R;

public class guardiansViewHolder extends RecyclerView.ViewHolder {

    public TextView guardianName;
    public TextView guardianPhone;

    public guardiansViewHolder(@NonNull View itemView) {
        super(itemView);

        guardianName = itemView.findViewById(R.id.tv_guardianName);
        guardianPhone = itemView.findViewById(R.id.tv_guardianPhone);

    }
}

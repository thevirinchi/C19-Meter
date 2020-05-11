package com.z3t4z00k.c19meter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RedZoneHolder extends RecyclerView.ViewHolder {
    private TextView district;

    RedZoneHolder(@NonNull View itemView) {
        super(itemView);
        district = itemView.findViewById(R.id.name);
    }

    void bind(ZoneModal zoneModal){
        district.setText(zoneModal.getDistrict());
    }
}

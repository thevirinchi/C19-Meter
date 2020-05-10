package com.z3t4z00k.c19meter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DistrictHolder extends RecyclerView.ViewHolder {

    private TextView district, total, cured, death;

    public DistrictHolder(@NonNull View itemView) {
        super(itemView);
        district = itemView.findViewById(R.id.name);
        total = itemView.findViewById(R.id.active);
        cured = itemView.findViewById(R.id.cured);
        death = itemView.findViewById(R.id.deaths);
    }

    void bind(DistrictModal districtModal){
        district.setText(districtModal.getDistrict());
        total.setText(districtModal.getT());
        cured.setText(districtModal.getC());
        death.setText(districtModal.getD());
    }
}

package com.z3t4z00k.c19meter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StateHolder extends RecyclerView.ViewHolder {

    private TextView state, total, cured, death;

    public StateHolder(@NonNull View itemView) {
        super(itemView);
        state = itemView.findViewById(R.id.name);
        total = itemView.findViewById(R.id.active);
        cured = itemView.findViewById(R.id.cured);
        death = itemView.findViewById(R.id.deaths);
    }

    void bind(StateModal stateModal){
        state.setText(stateModal.getState());
        total.setText(stateModal.getT());
        cured.setText(stateModal.getC());
        death.setText(stateModal.getD());
    }

}

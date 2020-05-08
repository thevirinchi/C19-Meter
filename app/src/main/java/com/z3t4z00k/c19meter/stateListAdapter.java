package com.z3t4z00k.c19meter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class stateListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<StateModal> stateModalList;

    stateListAdapter(Context context, List<StateModal> stateModals){
        this.context = context;
        this.stateModalList = stateModals;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_item_layout, parent, false);;
        return new StateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StateModal stateModal = stateModalList.get(position);
        ((StateHolder) holder).bind(stateModal);
    }

    @Override
    public int getItemCount() {
        return stateModalList.size();
    }
}

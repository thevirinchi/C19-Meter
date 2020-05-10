package com.z3t4z00k.c19meter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DistrictListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<DistrictModal> districtModalList;

    DistrictListAdapter(Context context, List<DistrictModal> districtModals){
        this.context = context;
        this.districtModalList = districtModals;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_item_layout, parent, false);;
        return new DistrictHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DistrictModal districtModal = districtModalList.get(position);
        ((DistrictHolder) holder).bind(districtModal);
    }

    @Override
    public int getItemCount() {
        return districtModalList.size();
    }
}

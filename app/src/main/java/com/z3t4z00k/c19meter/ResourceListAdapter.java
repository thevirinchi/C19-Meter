package com.z3t4z00k.c19meter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResourceListAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<ResourceModel> resourceModels;

    public ResourceListAdapter(Context context, List<ResourceModel> resourceList){
        this.mContext = context;
        this.resourceModels = resourceList;
    }

    @Override
    public int getItemCount() {
        return resourceModels.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item_layout, parent, false);
            return new ResourceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ResourceModel resource = resourceModels.get(position);
        ((ResourceHolder) holder).bind(resource, mContext);
    }
}


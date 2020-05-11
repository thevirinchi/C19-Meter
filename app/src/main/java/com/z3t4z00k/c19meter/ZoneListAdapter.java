package com.z3t4z00k.c19meter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ZoneListAdapter extends RecyclerView.Adapter{

    private static final String ZONE_COLOR_GREEN = "Green";
    private static final String ZONE_COLOR_ORANGE = "Orange";
    private static final String ZONE_COLOR_RED = "Red";
    private static final int VIEW_TYPE_ZONE_GREEN = 1;
    private static final int VIEW_TYPE_ZONE_ORANGE = 2;
    private static final int VIEW_TYPE_ZONE_RED = 3;

    private Context mContext;
    private List<ZoneModal> zoneModals;

    ZoneListAdapter(Context context, List<ZoneModal> zoneList){
        this.mContext = context;
        this.zoneModals = zoneList;
    }

    @Override
    public int getItemCount() {
        return zoneModals.size();
    }

    @Override
    public int getItemViewType(int position){
        ZoneModal zone = zoneModals.get(position);
        if(zone.getColor().equals(ZONE_COLOR_GREEN))
            return VIEW_TYPE_ZONE_GREEN;
        else if(zone.getColor().equals(ZONE_COLOR_ORANGE))
            return VIEW_TYPE_ZONE_ORANGE;
        else
            return VIEW_TYPE_ZONE_RED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_ZONE_GREEN){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zone_item_layout_green, parent, false);
            return new GreenZoneHolder(view);
        }
        else if(viewType == VIEW_TYPE_ZONE_ORANGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zone_item_layout_orange, parent, false);
            return new OrangeZoneHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zone_item_layout_red, parent, false);
        return new RedZoneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ZoneModal zone = zoneModals.get(position);
        switch(holder.getItemViewType()){
            case VIEW_TYPE_ZONE_GREEN:
                ((GreenZoneHolder) holder).bind(zone);
                break;
            case VIEW_TYPE_ZONE_ORANGE:
                ((OrangeZoneHolder) holder).bind(zone);
                break;
            case VIEW_TYPE_ZONE_RED:
                ((RedZoneHolder) holder).bind(zone);
                break;
        }
    }
}


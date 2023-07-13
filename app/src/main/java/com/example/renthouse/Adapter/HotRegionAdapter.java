package com.example.renthouse.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.OOP.Region;
import com.example.renthouse.R;

import java.util.List;

public class HotRegionAdapter extends RecyclerView.Adapter<HotRegionAdapter.HotRegionViewHolder> {
    List<Region> regionList;
    public HotRegionAdapter(List<Region> regionList) {
        this.regionList = regionList;
    }
    public void addData(Region region) {
        regionList.add(region);
        notifyItemInserted(regionList.indexOf(region));
    }
    @NonNull
    @Override
    public HotRegionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_region, parent, false);
        return new HotRegionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotRegionViewHolder holder, int position) {
        if (regionList.size() == 0) {
            return;
        } else {
            Region region = regionList.get(position);
            if(region == null){
                return;
            }
            holder.textViewStt.setText(String.valueOf(position + 1));
            holder.textViewDistrict.setText(region.getDistrictNameWithType());
            holder.textViewCity.setText(region.getCityNameWithType());
            holder.textViewAmountRoomOfTopRegion.setText(String.valueOf(region.getSoLuong()));
        }
    }

    @Override
    public int getItemCount() {
        if (regionList != null) {
            return regionList.size();
        }
        return 0;
    }

    public class HotRegionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStt;
        TextView textViewDistrict;
        TextView textViewCity;
        TextView textViewAmountRoomOfTopRegion;
        public HotRegionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStt = itemView.findViewById(R.id.textViewStt);
            textViewDistrict =itemView.findViewById(R.id.textViewDistrict);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewAmountRoomOfTopRegion = itemView.findViewById(R.id.textViewAmountRoomOfTopRegion);
        }
    }
}

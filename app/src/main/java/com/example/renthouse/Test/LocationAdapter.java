package com.example.renthouse.Test;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable {
    private List<Location> mListLocation;
    private List<Location> filteredLocation;

    public LocationAdapter(List<Location> mListLocation) {
        this.mListLocation = mListLocation;
        this.filteredLocation = mListLocation;
    }
    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // dua layout vao listView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_item, parent, false);
        return new LocationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        // set du lieu len
        Location location = mListLocation.get(position);
        if (location == null) {
            return;
        }
        holder.diachi.setText(location.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    @Override
    public int getItemCount() {
        if (mListLocation != null) {
            return mListLocation.size();
        }
        return 0;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    filteredLocation = mListLocation;
                } else {
                    List<Location> list = new ArrayList<>();
                    for (Location location : filteredLocation) {
                        if(location.getAddress().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(location);
                        }
                    }
                    mListLocation = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListLocation;

                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListLocation = (List<Location>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView diachi;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            diachi = itemView.findViewById(R.id.diachi);
        }
    }

}

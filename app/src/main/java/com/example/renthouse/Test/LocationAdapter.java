package com.example.renthouse.Test;

import static android.view.View.GONE;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.JSONReader.JSONReaderLocation;
import com.example.renthouse.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable {
    private List<Location> listLocation;
    private List<Location> listHistoryLocation;
    private List<Location> listLocationDatabase;
    private boolean searchViewIsEmpty;
    private Context context;
    public LocationAdapter(List<Location> mListLocation, Context context, String currentLocation) {
        this.listHistoryLocation = mListLocation;
        this.listLocation = mListLocation;
        this.context = context;
        JSONReaderLocation jsonReaderLocation = new JSONReaderLocation(this.context);
        jsonReaderLocation.readDatabaseLocation(currentLocation);
        this.listLocationDatabase = jsonReaderLocation.getLocationList();
        searchViewIsEmpty = true;
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
        Location location = listLocation.get(position);
        if (location == null) {
            return;
        }
        if (searchViewIsEmpty) {
            holder.xoa.setVisibility(View.VISIBLE);
        } else {
            holder.xoa.setVisibility(View.INVISIBLE);
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
        if (listLocation != null) {
            return listLocation.size();
        }
        return 0;
    }

    // Hàm lọc dữ liệu
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    listLocation = listHistoryLocation; // list filteredLocation này sẽ chứa các dữ liệu đọc từ file json
                    searchViewIsEmpty = true;
                } else {
                    searchViewIsEmpty = false;
                    List<Location> list = new ArrayList<>();
                    for (Location location : listLocationDatabase) {
                        String normalizedElement = Normalizer.normalize(location.getAddress(), Normalizer.Form.NFC);
                        String normalizedSearchString = Normalizer.normalize(strSearch, Normalizer.Form.NFC);

                        if (normalizedElement.toLowerCase().contains(normalizedSearchString.toLowerCase())) {
                            list.add(location);
                        }
                    }
                    listLocation = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listLocation;

                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listLocation = (List<Location>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView diachi;
        private TextView xoa;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            diachi = itemView.findViewById(R.id.diachi);
            xoa = itemView.findViewById(R.id.xoaBtn);

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listHistoryLocation.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }

}

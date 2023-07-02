package com.example.renthouse.Test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Interface.IClickItemAddressListener;
import com.example.renthouse.JSONReader.JSONReaderLocation;
import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable {
    private List<Location> listLocation;
    private List<Location> listHistoryLocation;
    private List<Location> listLocationDatabase;
    public boolean searchViewIsEmpty;
    private Context context;
    private IClickItemAddressListener mListener;
    private PreferenceManager preferenceManager;

    public LocationAdapter(List<Location> mListLocation, Context context, String currentLocation, IClickItemAddressListener listener) {
        this.listHistoryLocation = mListLocation;
        this.listLocation = mListLocation;
        this.mListener = listener;
        this.context = context;
        JSONReaderLocation jsonReaderLocation = new JSONReaderLocation(this.context);
        jsonReaderLocation.readDatabaseLocation(currentLocation);
        this.listLocationDatabase = jsonReaderLocation.getLocationList();
        searchViewIsEmpty = true;
        this.preferenceManager = new PreferenceManager(context);
    }
    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // dua layout vao listView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_item, parent, false);
        return new LocationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        holder.diachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHistory(String.valueOf(holder.diachi.getText()));
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
    private void updateListHistoryLocation() {
        String jsonString = preferenceManager.getString(Constants.KEY_HISTORY_SEARCH);
        if (jsonString != null) {
            // Convert JSON string to List
            List<String> listHistory = new Gson().fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
            listHistoryLocation.clear();
            for (String location : listHistory) {
                listHistoryLocation.add(new Location(location));
            }
            notifyDataSetChanged();
        }
    }
    // Hàm lọc dữ liệu
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    updateListHistoryLocation();
                    listLocation = listHistoryLocation;
                    notifyDataSetChanged();
                    searchViewIsEmpty = true;
                } else {
                    searchViewIsEmpty = false;
                    List<Location> list = new ArrayList<>();
                    for (Location location : listLocationDatabase) {
                        String normalizedElement = removeDiacritics(location.getAddress());
                        String normalizedSearchString = removeDiacritics(strSearch);

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
                if (listLocation != null) {
                    mListener.onFilterCount(listLocation.size());
                }
                notifyDataSetChanged();
            }
        };
    }

    public String removeDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("\\p{M}", "");
        return str;
    }

    public void saveHistory(String historyLocation) {
        if (mListener != null) {
            boolean flag = false;
            String address = String.valueOf(historyLocation);
            for (Location location : listHistoryLocation) {
                if (location.getAddress().equals(address)) {
                    listHistoryLocation.remove(location);
                    listHistoryLocation.add(0, location);
                    flag = true;
                    notifyDataSetChanged();
                    break;
                }
            }
            if (!flag) {
                listHistoryLocation.add(0, new Location(address));
                notifyDataSetChanged();
            }
            LinkedHashSet<String> listHistory = castListStringLocation();
            String jsonString = new Gson().toJson(listHistory);
            preferenceManager.putString(Constants.KEY_HISTORY_SEARCH, jsonString);
            mListener.onItemClick(address);
        }
    }

    public void saveHistoryLocation(String query) {

        if (mListener != null) {
            boolean flag = false;
            String address = String.valueOf(query);
            for (Location location : listHistoryLocation) {
                if (location.getAddress().equals(address)) {
                    listHistoryLocation.remove(location);
                    listHistoryLocation.add(0, location);
                    flag = true;
                    notifyDataSetChanged();
                    break;
                }
            }
            if (!flag) {
                listHistoryLocation.add(0, new Location(address));
                notifyDataSetChanged();
            }
            LinkedHashSet<String> listHistory = castListStringLocation();
            String jsonString = new Gson().toJson(listHistory);
            preferenceManager.putString(Constants.KEY_HISTORY_SEARCH, jsonString);
        }
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
                        LinkedHashSet<String> listHistory = castListStringLocation();
                        String jsonString = new Gson().toJson(listHistory);
                        preferenceManager.putString(Constants.KEY_HISTORY_SEARCH, jsonString);
                    }
                }
            });
        }
    }
    private LinkedHashSet<String> castListStringLocation() {
        LinkedHashSet<String> locationSet = new LinkedHashSet<>();
        for (int i = 0; i < listHistoryLocation.size(); i++) {
            String address =  String.valueOf(listHistoryLocation.get(i).getAddress());
            locationSet.add(address);
        }
        return  locationSet;
    }
}

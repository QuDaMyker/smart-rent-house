package com.example.renthouse.Test;

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
import com.example.renthouse.SharedPreferences.DataLocalManager;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable {
    private List<Location> listLocation;
    private List<Location> listHistoryLocation;
    private List<Location> listLocationDatabase;
    private boolean searchViewIsEmpty;
    private Context context;
    private IClickItemAddressListener mListener;

    public LocationAdapter(List<Location> mListLocation, Context context, String currentLocation, IClickItemAddressListener listener) {
        this.listHistoryLocation = mListLocation;
        this.listLocation = mListLocation;
        this.mListener = listener;
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
            Set<String> list= DataLocalManager.getSearchHistory();
            Log.d("Số lượng phần tử", String.valueOf(list.size()));
            listHistoryLocation.clear();
            for (String s : list) {
                listHistoryLocation.add(new Location(s));
            }
            holder.xoa.setVisibility(View.VISIBLE);
        } else {
            holder.xoa.setVisibility(View.INVISIBLE);
        }
        holder.diachi.setText(location.getAddress());
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
                    listLocation = listHistoryLocation;
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
            diachi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            DataLocalManager.addSearchHistory(String.valueOf(diachi.getText()));
                            mListener.onItemClick(String.valueOf(diachi.getText()));
                        }
                    }
                }
            });
        }
    }

}

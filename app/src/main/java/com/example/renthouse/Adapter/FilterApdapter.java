package com.example.renthouse.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Interface.IClickDeleteItemFilterListener;
import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FilterApdapter extends RecyclerView.Adapter<FilterApdapter.FilterViewHolder> {

    private List<String> filters;
    private HashMap<String, List<String>> hashMapFilter;
    private boolean[] flag = new boolean[5];
    private final IClickDeleteItemFilterListener mListener;
    @SuppressLint("NotifyDataSetChanged")
    public void setData(String s, int flag) {
        switch (flag) {
            case 0: // Đặt cờ của price = true
                updateValueWhenExists("Price", 0, s);
                break;
            case 1:
                updateValueUtilities(s);
                break;
            case 2:
                updateValueWhenExists("Type", 2, s);
                break;
            case 3:
                updateValueWhenExists("Amount", 3, s);
                break;
            case 4:
                updateValueWhenExists("Sort", 4, s);
                break;
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateValueUtilities(String content) {
        if (this.flag[1]) {
            hashMapFilter.get("Utilities").add(content);
            filters.add(content);
        } else {
            List<String> list = new ArrayList<>();
            list.add(content);
            hashMapFilter.put("Utilities", list);
            filters.add(content);
            this.flag[1] = true;
        }
        notifyDataSetChanged();
    }
    public void deleteItemtUtilities(String content) {
        List<String> utilities = hashMapFilter.get("Utilities");
        if (utilities != null && utilities.contains(content)) {
            utilities.remove(content);
        }
        if (utilities == null || utilities.isEmpty()) {
            this.flag[1] = false;
        }
        int position = filters.indexOf(content);
        filters.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filters.size());
    }
    private void updateValueWhenExists(String key, int index, String content) {
        if (content == null) {
            return;
        }
        if (this.flag[index]) {
            List<String> list = hashMapFilter.get(key);
            int position = filters.indexOf(list.get(0));
            filters.set(position, content);
        } else {
            this.flag[index] = true;
            filters.add(content);
        }
        hashMapFilter.put(key, new ArrayList<>(Collections.singleton(content)));
    }
    public FilterApdapter(IClickDeleteItemFilterListener itemFilterListener) {
        filters = new ArrayList<String>();
        hashMapFilter = new HashMap<>();
        Arrays.fill(flag, false);
        this.mListener = itemFilterListener;
    }
    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_choose, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String content = filters.get(position);
        if (content == null){
            return;
        }
        holder.textViewContent.setText(content);
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleData(content);
                filters.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, filters.size());
            }
        });
    }
    private void handleData(String content) {
        if (hashMapFilter.get("Price") != null && hashMapFilter.get("Price").contains(content)) {
            hashMapFilter.get("Price").remove(content);
            this.flag[0] = false;
            mListener.onItemDeletePriceListener();
        } else if (hashMapFilter.get("Type") != null && hashMapFilter.get("Type").contains(content)) {
            hashMapFilter.get("Type").remove(content);
            Log.d("Delete", "1");
            this.flag[2] = false;
            mListener.onItemDeleteTypeListener();
        } else if (hashMapFilter.get("Amount") != null && hashMapFilter.get("Amount").contains(content)) {
            hashMapFilter.get("Amount").remove(content);
            this.flag[3] = false;
            mListener.onItemDeleteAmountListener();
        } else if (hashMapFilter.get("Sort") != null && hashMapFilter.get("Sort").contains(content)) {
            hashMapFilter.get("Sort").remove(content);
            this.flag[4] = false;
            mListener.onItemDeleteSortListener();
        } else {
            List<String> utilities = hashMapFilter.get("Utilities");
            if (utilities != null && utilities.contains(content)) {
                utilities.remove(content);
                mListener.onItemDeleteUtilitiesListener(content);
            }
            if (utilities == null || utilities.isEmpty()) {
                this.flag[1] = false;
            }

        }
    }

    @Override
    public int getItemCount() {
        if (filters != null) {
             return filters.size();
        }
        return 0;
    }

    public void clearData() {
        filters.clear();
        hashMapFilter.clear();
        Arrays.fill(flag, false);
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent;
        ImageButton buttonDelete;
        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.textViewContentChoice);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteFilterChoice);
        }
    }
}

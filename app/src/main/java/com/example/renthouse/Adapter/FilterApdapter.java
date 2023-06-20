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

import com.example.renthouse.Interface.IClickItemFilterListener;
import com.example.renthouse.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PropertyPermission;
import java.util.Set;

public class FilterApdapter extends RecyclerView.Adapter<FilterApdapter.FilterViewHolder> {

    private List<String> filters;
    private HashMap<String, List<String>> hashMapFilter;
    private boolean[] flag = new boolean[5];
    private IClickItemFilterListener mListener;
    public void setData(String s, int flag) {
        switch (flag) {
            case 0: // Đặt cờ của price = true
                updateValueWhenExists("Price", 0, s);
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
    public void setDataUtilites(List<String> utilities) {
        if (this.flag[1]) {

            // Thay đổi giá trị của utilities trong mảng filters
            List<String> list = hashMapFilter.get("Utilities");
            filters.removeAll(list);
            filters.addAll(utilities);
            hashMapFilter.put("Utilities", utilities);
        } else {
            this.flag[1] = true;
            for(String utility : utilities) {
                filters.add(utility);
            }
            hashMapFilter.put("Utilities", utilities);
        }
        notifyDataSetChanged();
    }
    private void updateValueWhenExists(String key, int index, String content) {
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
    public FilterApdapter(IClickItemFilterListener itemFilterListener) {
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
        if (hashMapFilter.get("Price").contains(content)) {
            hashMapFilter.get("Price").remove(content);
            Log.d("Price", "a");
            mListener.onItemDeletePriceListener();
            this.flag[0] = false;
        } else if (hashMapFilter.get("Type").contains(content)) {
            hashMapFilter.get("Type").remove(content);
            mListener.onItemDeleteTypeListener();
            this.flag[2] = false;
        } else if (hashMapFilter.get("Amount").contains(content)) {
            hashMapFilter.get("Amount").remove(content);
            mListener.onItemDeleteAmountListener();
            this.flag[3] = false;
        } else if (hashMapFilter.get("Sort").contains(content)) {
            hashMapFilter.get("Sort").remove(content);
            mListener.onItemDeleteSortListener();
            this.flag[4] = false;
        } else {

        }
    }

    @Override
    public int getItemCount() {
        if (filters != null) {
             return filters.size();
        }
        return 0;
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

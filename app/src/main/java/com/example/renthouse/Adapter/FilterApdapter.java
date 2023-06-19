package com.example.renthouse.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterApdapter extends RecyclerView.Adapter<FilterApdapter.FilterViewHolder> {

    public List<String> filters;
    private boolean[] flag;
    public void setData(String s) {
        filters.add(s);
        notifyDataSetChanged();
    }
    public FilterApdapter() {
        filters = new ArrayList<String>();
        Arrays.fill(flag, false);
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
                filters.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, filters.size());
            }
        });
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

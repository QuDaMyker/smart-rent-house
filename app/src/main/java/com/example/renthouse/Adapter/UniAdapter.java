package com.example.renthouse.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.OOP.University;
import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.List;

public class UniAdapter extends RecyclerView.Adapter<UniAdapter.ViewHolder> {
    private List<University> itemList;
    public List<University> filteredList;
    public LinearLayout introView;
    public TextView helpTv;
    public interface OnItemClickListener {
        void onItemClick(int position, University data);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public UniAdapter(List<University> itemList) {
        this.itemList = itemList;
        this.filteredList = new ArrayList<>(itemList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tenTruong);
            itemAddress = itemView.findViewById(R.id.diachiTruong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            University u = filteredList.get(position);
                            listener.onItemClick(position, u);
                        }
                    }
                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_uni, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        University item = filteredList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemAddress.setText(item.getAddress());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String keyword) {
        filteredList.clear();
        if (TextUtils.isEmpty(keyword)) {
            filteredList.clear();
        } else {
            keyword = keyword.toLowerCase();
            for (University item : itemList) {
                if (item.getName().toLowerCase().contains(keyword)) {
                    filteredList.add(item);
                }
            }
        }

        if (introView != null) {
            if (filteredList.isEmpty()) {
                if(TextUtils.isEmpty(keyword)){
                    introView.setVisibility(View.VISIBLE);
                    helpTv.setVisibility(View.GONE);
                }
                else{
                    introView.setVisibility(View.GONE);
                    helpTv.setVisibility(View.VISIBLE);
                }
            } else {
                introView.setVisibility(View.GONE);
                helpTv.setVisibility(View.GONE);
            }
        }
    }

    public void setIntroView(LinearLayout introView){
        this.introView = introView;
    }
    public void setHelpTv(TextView helpTv){
        this.helpTv = helpTv;
    }
}

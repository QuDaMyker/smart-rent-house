package com.example.renthouse.Admin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.UniAdapter;
import com.example.renthouse.Admin.OOP.NotiSchedule;
import com.example.renthouse.OOP.University;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class NotiScheduleAdapter extends RecyclerView.Adapter<NotiScheduleAdapter.ViewHolder> {

    private List<NotiSchedule> itemList;
//    public interface OnEditButtonClickListener {
//        void onEditButtonClick(int position);
//    }
//    private OnEditButtonClickListener editButtonClickListener;
//    public void setOnEditButtonClickListener(OnEditButtonClickListener listener) {
//        this.editButtonClickListener = listener;
//    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }
    private OnDeleteButtonClickListener deleteButtonClickListener;
    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }
    public NotiScheduleAdapter(List<NotiSchedule> itemList){
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public NotiScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noti_schedule, parent, false);
        return new NotiScheduleAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiScheduleAdapter.ViewHolder holder, int position) {
        NotiSchedule item = itemList.get(position);
        holder.titleTv.setText(item.getTitle());
        holder.contentTv.setText(item.getContent());
        holder.timeTv.setText(item.getTime());
        holder.receiverTv.setText(item.getReceiver());
        if (item.getLoop().equals("Không bao giờ")){
            holder.loopTv.setText(item.getDate());
        }
        else {
            holder.loopTv.setText(item.getLoop());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        TextView contentTv;
        TextView timeTv;
        TextView receiverTv;
        TextView loopTv;
        //MaterialButton editBtn;
        MaterialButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.titleTv);
            contentTv = itemView.findViewById(R.id.contentTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            receiverTv = itemView.findViewById(R.id.receiverTv);
            loopTv = itemView.findViewById(R.id.loopTv);
            //editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);

//            editBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (editButtonClickListener != null) {
//                        editButtonClickListener.onEditButtonClick(getAdapterPosition());
//                    }
//                }
//            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deleteButtonClickListener != null) {
                        deleteButtonClickListener.onDeleteButtonClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}

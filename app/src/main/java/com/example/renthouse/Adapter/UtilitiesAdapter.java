package com.example.renthouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.R;

import java.util.List;

public class UtilitiesAdapter extends  RecyclerView.Adapter<UtilitiesAdapter.UtilitiesViewHolder>{
    private List<String> listUti;
    private Context context;
    private int itemToShow = 2;

    public UtilitiesAdapter (List<String> item, Context context)
    {
        listUti = item;
        this.context = context;
    }

    @NonNull
    @Override
    public UtilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_utilities, parent, false);

        return  new UtilitiesViewHolder(v);
    }

    public void setLimit(int limit) {
        this.itemToShow = limit;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull UtilitiesViewHolder holder, int position) {

        String item = listUti.get(position);

        if (item.equals("WC riêng")) {
            holder.iconImageView.setImageResource(R.drawable.ic_wc);
            holder.nameTextView.setText(item);

        } else if (item.equals("WC riêng")){
            holder.iconImageView.setImageResource(R.drawable.ic_wc);
            holder.nameTextView.setText(item);

        }else if (item.equals("Cửa sổ")){
            holder.iconImageView.setImageResource(R.drawable.ic_window);
            holder.nameTextView.setText(item);

        }else if (item.equals("Wifi")){
            holder.iconImageView.setImageResource(R.drawable.ic_wifi);
            holder.nameTextView.setText(item);

        }else if (item.equals("Nhà bếp")){
            holder.iconImageView.setImageResource(R.drawable.ic_kitchen);
            holder.nameTextView.setText(item);

        }else if (item.equals("Máy giặt")){
            holder.iconImageView.setImageResource(R.drawable.ic_laundry);
            holder.nameTextView.setText(item);

        }else if (item.equals("Tủ lạnh")){
            holder.iconImageView.setImageResource(R.drawable.ic_fridge);
            holder.nameTextView.setText(item);

        }else if (item.equals("Chỗ để xe")){
            holder.iconImageView.setImageResource(R.drawable.ic_motobike);
            holder.nameTextView.setText(item);

        }else if (item.equals("An ninh")){
            holder.iconImageView.setImageResource(R.drawable.ic_security);
            holder.nameTextView.setText(item);

        }else if (item.equals("Tự do")){
            holder.iconImageView.setImageResource(R.drawable.ic_timer);
            holder.nameTextView.setText(item);

        }else if (item.equals("Chủ riêng")){
            holder.iconImageView.setImageResource(R.drawable.outline_person_24);
            holder.nameTextView.setText(item);

        }else if (item.equals("Gác lửng")){
            holder.iconImageView.setImageResource(R.drawable.ic_entresol);
            holder.nameTextView.setText(item);

        }else if (item.equals("Thú cưng")){
            holder.iconImageView.setImageResource(R.drawable.ic_pet);
            holder.nameTextView.setText(item);

        }else if (item.equals("Giường")){
            holder.iconImageView.setImageResource(R.drawable.ic_bed);
            holder.nameTextView.setText(item);

        }else if (item.equals("Tủ đồ")){
            holder.iconImageView.setImageResource(R.drawable.ic_wardrobe);
            holder.nameTextView.setText(item);

        }else if (item.equals("Máy lạnh")){
            holder.iconImageView.setImageResource(R.drawable.ic_cool);
            holder.nameTextView.setText(item);

        }
    }

    @Override
//    public int getItemCount() {
//        if (listUti != null) {
//            return listUti.size();
//        } else {
//            return 0;
//        }
//    }
//    public int getItemCount() {
//        if (itemToShow > 0 && itemToShow < listUti.size()) {
//            return itemToShow;
//        } else {
//            return listUti.size();
//        }
//    }
    public int getItemCount() {
        if (itemToShow > 0)
        {
            return itemToShow;
        }
        return 0;
    }


    public class UtilitiesViewHolder extends RecyclerView.ViewHolder{
        public ImageView iconImageView;
        public TextView nameTextView;

        public UtilitiesViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }


}

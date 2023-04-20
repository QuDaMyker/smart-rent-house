package com.example.renthouse.Activity;

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

public class RecyclerDataAdapter  extends RecyclerView.Adapter<RecyclerDataAdapter.DataViewHolder> {


    private List <Room> rooms;
    private Context context;

    //public RecyclerDataAdapter(LikedRoomsFragment f, List<Room> rooms){
       // Context context1 = f.getActivity();
        //this.context = context1;
        //this.rooms = rooms;
    //}
    @NonNull
    @Override
    public  RecyclerDataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_name,parent, false);

        return new DataViewHolder(itemView);
    }

    @Override

    public void onBindViewHolder(@NonNull RecyclerDataAdapter.DataViewHolder holder, int position) {
        String name = rooms.get (position).getName();
        String address = rooms.get(position).getAddress();
        String price = rooms.get(position).getPrice().toString();
        String srcImage = rooms.get(position).getSourceImage();
        holder.tvName.setText(name);
        holder.tvAddress.setText(address);
        holder.tvPrice.setText(price);
        holder.ivImage.setImageResource(R.drawable.p1);
    }

    @Override
    public int getItemCount() {
        return rooms == null ? 0 : rooms.size();
    }
    //DataViewHolder Class
    public class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvPrice;
        private ImageView ivImage;

        public DataViewHolder(View itemView){
            super (itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }
}

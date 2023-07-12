package com.example.renthouse.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.OutstandingRoomAdapter;
import com.example.renthouse.Admin.Activity.Admin_ActivityThongTinPhong;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;

public class PhongTroAdapter extends RecyclerView.Adapter<PhongTroAdapter.PhongTroViewHolder> {
    private Context context;
    private List<Room> phongtro;
    private final ItemClick itemClick;

    public PhongTroAdapter(Context context, List<Room> phongtro, ItemClick itemClick) {
        this.context = context;
        this.phongtro = phongtro;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public PhongTroAdapter.PhongTroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phongtro, parent, false);
        return new PhongTroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongTroAdapter.PhongTroViewHolder holder, int position) {
        Room room = phongtro.get(position);

        long price = (long)room.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(price);
        String priceString = formattedNumber.replaceAll(",", ".");
        priceString += " đ";
        holder.textView_tienphong.setText(priceString);

        if (!room.isRented()) {
            holder.textView_tinhtrang.setText("Còn phòng");
        }
        else {
            holder.textView_tinhtrang.setText("Đã thuê");
        }

        holder.textView_ngaydang.setText(room.getDateTime());
        holder.textView_tenphong.setText(room.getTitle());
        holder.textView_diachiphong.setText(room.getLocation().getWard().getPath_with_type());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetails(room);
            }
        });

    }
    private void goToDetails(Room room) {
        Intent intent = new Intent(context, Admin_ActivityThongTinPhong.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedRoom", room);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return phongtro.size();
    }

    public class PhongTroViewHolder extends RecyclerView.ViewHolder {
        TextView textView_tienphong;
        TextView textView_ngaydang;
        TextView textView_tinhtrang;
        TextView textView_tenphong;
        TextView textView_diachiphong;
        LinearLayout item;

        public PhongTroViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_tienphong = itemView.findViewById(R.id.textView_tienphong);
            textView_ngaydang = itemView.findViewById(R.id.textView_ngaydang);
            textView_tinhtrang = itemView.findViewById(R.id.textView_tinhtrang);
            textView_tenphong = itemView.findViewById(R.id.textView_tenphong);
            textView_diachiphong = itemView.findViewById(R.id.textView_diachiphong);
            item = itemView.findViewById(R.id.item_phongtro);
        }
    }
}

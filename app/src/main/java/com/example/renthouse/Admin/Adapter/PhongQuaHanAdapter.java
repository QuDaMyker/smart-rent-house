package com.example.renthouse.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;

import java.text.DecimalFormat;
import java.util.List;

public class PhongQuaHanAdapter extends RecyclerView.Adapter<PhongQuaHanAdapter.PhongQuaHanViewHolder> {
    private Context context;
    private List<Room> phongtro;

    public PhongQuaHanAdapter(Context context, List<Room> phongtro) {
        this.context = context;
        this.phongtro = phongtro;
    }

    @NonNull
    @Override
    public PhongQuaHanAdapter.PhongQuaHanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phongchoduyet, parent, false);
        return new PhongQuaHanAdapter.PhongQuaHanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongQuaHanAdapter.PhongQuaHanViewHolder holder, int position) {
        Room room = phongtro.get(position);

        long price = (long)room.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(price);
        String priceString = formattedNumber.replaceAll(",", ".");
        priceString += " đ";
        holder.textView_tienphong.setText(priceString);

        holder.textView_ngaydang.setText(room.getDateTime());
        holder.textView_tenphong.setText(room.getTitle());
        holder.textView_diachiphong.setText(room.getLocation().getWard().getPath_with_type());
    }

    @Override
    public int getItemCount() {
        return phongtro.size();
    }

    public class PhongQuaHanViewHolder extends RecyclerView.ViewHolder {
        TextView textView_tienphong;
        TextView textView_ngaydang;
        TextView textView_tenphong;
        TextView textView_diachiphong;
        public PhongQuaHanViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_tienphong = itemView.findViewById(R.id.textView_tienphong);
            textView_ngaydang = itemView.findViewById(R.id.textView_ngaydang);
            textView_tenphong = itemView.findViewById(R.id.textView_tenphong);
            textView_diachiphong = itemView.findViewById(R.id.textView_diachiphong);
        }
    }
}

package com.example.renthouse.Admin.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.Admin.listeners.ItemNguoiDungListener;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ItemContainerDanhsachnguoidungBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> {
    private List<NguoiDung> list;
    private Context context;
    private int colorRootLayout;
    private final ItemNguoiDungListener itemNguoiDungListener;

    public NguoiDungAdapter(Context context, List<NguoiDung> list, int colorRootLayout, ItemNguoiDungListener itemNguoiDungListener) {
        this.list = list;
        this.context = context;
        this.colorRootLayout = colorRootLayout;
        this.itemNguoiDungListener = itemNguoiDungListener;
    }

    @NonNull
    @Override
    public NguoiDungAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemContainerDanhsachnguoidungBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerDanhsachnguoidungBinding binding;

        public ViewHolder(ItemContainerDanhsachnguoidungBinding itemContainerDanhsachnguoidungBinding) {
            super(itemContainerDanhsachnguoidungBinding.getRoot());
            binding = itemContainerDanhsachnguoidungBinding;
        }

        void setData(NguoiDung nguoiDung) {
            Picasso.get().load(nguoiDung.getAccountClass().getImage()).into(binding.imageUser);
            binding.nameUser.setText(nguoiDung.getAccountClass().getFullname());
            binding.countRoom.setText(nguoiDung.getSoLuongPhong()+"");
            binding.dateJoinUser.setText(nguoiDung.getAccountClass().getNgayTaoTaiKhoan());
            binding.rootLayout.setBackgroundResource(R.drawable.bg_radius_secondary90);
            binding.getRoot().setOnClickListener(v -> {
                NguoiDung nguoiDungIntent = new NguoiDung();
                nguoiDungIntent.setKey(nguoiDungIntent.getKey());
                nguoiDungIntent.setAccountClass(nguoiDung.getAccountClass());
                nguoiDungIntent.setSoLuongPhong(nguoiDung.getSoLuongPhong());
                itemNguoiDungListener.onItemNguoiDungClick(nguoiDungIntent);
            });
        }
    }
}

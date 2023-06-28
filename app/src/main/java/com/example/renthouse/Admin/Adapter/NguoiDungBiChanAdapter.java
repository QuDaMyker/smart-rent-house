package com.example.renthouse.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.Admin.listeners.ItemNguoiDungListener;
import com.example.renthouse.databinding.ItemContainerDanhsachnguoidungbichanBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NguoiDungBiChanAdapter extends RecyclerView.Adapter<NguoiDungBiChanAdapter.ViewHolder> {
    private List<NguoiDung> list;
    private Context context;
    private final ItemNguoiDungListener itemNguoiDungListener;

    public NguoiDungBiChanAdapter(Context context, List<NguoiDung> list, ItemNguoiDungListener itemNguoiDungListener) {
        this.list = list;
        this.context = context;
        this.itemNguoiDungListener = itemNguoiDungListener;
    }

    @NonNull
    @Override
    public NguoiDungBiChanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemContainerDanhsachnguoidungbichanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungBiChanAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerDanhsachnguoidungbichanBinding binding;

        public ViewHolder(ItemContainerDanhsachnguoidungbichanBinding itemContainerDanhsachnguoidungbichanBinding) {
            super(itemContainerDanhsachnguoidungbichanBinding.getRoot());
            binding = itemContainerDanhsachnguoidungbichanBinding;
        }

        void setData(NguoiDung nguoiDung) {
            Picasso.get().load(nguoiDung.getAccountClass().getImage()).into(binding.imageUser);
            binding.nameUser.setText("nguoiDung.getAccountClass().getFullname()");
            binding.countRoom.setText(nguoiDung.getSoLuongPhong() + "");
            binding.dateJoinUser.setText(nguoiDung.getAccountClass().getNgayTaoTaiKhoan());
            binding.getRoot().setOnClickListener(v -> {
                NguoiDung nguoiDungIntent = new NguoiDung();
                nguoiDungIntent.setKey(nguoiDung.getKey());
                nguoiDungIntent.setAccountClass(nguoiDung.getAccountClass());
                nguoiDungIntent.setSoLuongPhong(nguoiDung.getSoLuongPhong());
                itemNguoiDungListener.onItemNguoiDungClick(nguoiDungIntent);
            });
        }
    }
}

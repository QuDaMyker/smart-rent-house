package com.example.renthouse.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.OOP.Reports;
import com.example.renthouse.databinding.ItemContainerBaocaonguoidungBinding;

import java.util.List;

public class BaoCaoNguoiDungAdapter extends RecyclerView.Adapter<BaoCaoNguoiDungAdapter.ViewHolder> {
    private Context context;
    private List<Reports> list;

    public BaoCaoNguoiDungAdapter(Context context, List<Reports> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BaoCaoNguoiDungAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemContainerBaocaonguoidungBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaoCaoNguoiDungAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerBaocaonguoidungBinding binding;

        public ViewHolder(ItemContainerBaocaonguoidungBinding itemContainerBaocaonguoidungBinding) {
            super(itemContainerBaocaonguoidungBinding.getRoot());
            binding = itemContainerBaocaonguoidungBinding;
        }
        void setData(Reports reports) {
            binding.tvTitle.setText(reports.getTitle());
            binding.tvContent.setText(reports.getContent());
            binding.nameUser.setText(reports.getAccount().getFullname());
            binding.dateReport.setText(reports.getNgayBaoCao());
        }
    }
}

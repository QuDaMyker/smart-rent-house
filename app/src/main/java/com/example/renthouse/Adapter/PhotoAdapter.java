package com.example.renthouse.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>{
    private Context context;
    private List<Uri> listPhoto;

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }

    private OnButtonClickListener buttonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    public PhotoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Uri> list){
        this.listPhoto = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Uri uri = listPhoto.get(position);
        if(uri == null){
            return;
        }
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {

        }
        if(bitmap != null){
            holder.imgPhoto.setImageBitmap(bitmap);
        }
        else{
            Picasso.get().load(uri.toString()).into(holder.imgPhoto);
        }

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonClickListener != null){
                    buttonClickListener.onButtonClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listPhoto != null){
            return listPhoto.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPhoto;
        private Button btnRemove;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}

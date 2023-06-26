package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.renthouse.R;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivityFullImage extends AppCompatActivity {
    final Context context = this;
    private List<String> src;
    int curImage = 0;



    ImageButton btnPreScreen;
    ImageButton btnNextImage;
    ImageButton btnPreImage;

    TextView stt;

    TouchImageView ivImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        btnPreScreen = findViewById(R.id.btnBack);
        btnNextImage = findViewById(R.id.btnNextImage);
        btnPreImage = findViewById(R.id.btnPreImage);
        ivImages = findViewById(R.id.image);
        stt = findViewById(R.id.tvIndex);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("listImages")) {
            Bundle bundle = intent.getExtras();

            if (bundle != null && bundle.containsKey("listImages") && bundle.containsKey("curImage")) {
                src = bundle.getStringArrayList("listImages");
                curImage = bundle.getInt("curImage");
            }
        }

        if (curImage ==0 )
        {
            btnPreImage.setVisibility(View.GONE);
        }

        String temp = src.get(curImage);
        stt.setText(String.valueOf(curImage+1) +"/" + src.size());

        if (curImage == 0 )
        {
            btnPreImage.setVisibility(View.GONE);
        }

        Picasso.get().load(temp).into(ivImages);

        btnPreScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnPreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curImage--;
                stt.setText(String.valueOf(curImage + 1) +"/" + src.size());
                String temp = src.get(curImage);
                Picasso.get().load(temp).into(ivImages);
                if (curImage != src.size()-1)
                {
                    btnNextImage.setVisibility(View.VISIBLE);
                }
                if (curImage == 0)
                {
                    btnPreImage.setVisibility(View.GONE);
                }
                else {
                    btnPreImage.setVisibility(View.VISIBLE);
                }
            }
        });
        btnNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curImage++;
                stt.setText(String.valueOf(curImage+1) +"/" + src.size());
                String temp = src.get(curImage);
                Picasso.get().load(temp).into(ivImages);
                if (curImage != 0){
                    btnPreImage.setVisibility(View.VISIBLE);
                }
                if (curImage == src.size() -1) {
                    btnNextImage.setVisibility(View.GONE);
                }
                else {
                    btnNextImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
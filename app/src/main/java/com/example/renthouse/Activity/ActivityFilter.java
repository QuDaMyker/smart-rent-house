package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import com.example.renthouse.R;


public class ActivityFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Button buttonPrice = findViewById(R.id.buttonPrice);

        buttonPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HorizontalScrollView horizontalScrollViewResult = findViewById(R.id.horizontalDisplayResult);
                horizontalScrollViewResult.setVisibility(View.GONE);
            }
        });
    }
}
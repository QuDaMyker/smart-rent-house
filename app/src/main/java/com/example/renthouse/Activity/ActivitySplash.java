package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.renthouse.R;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        int totalProgressTime = 10000; // thời gian tối đa (ms)
        int intervalTime = 10; // thời gian giữa mỗi lần cập nhật (ms)
        int increment = 1; // giá trị tăng của ProgressBar

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            int progress = 0;

            public void run() {
                progressBar.setProgress(progress);
                if (progress == progressBar.getMax()) {
                    Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    progress += increment;
                    handler.postDelayed(this, intervalTime);
                }
            }
        };
        handler.postDelayed(runnable, intervalTime);
    }
}
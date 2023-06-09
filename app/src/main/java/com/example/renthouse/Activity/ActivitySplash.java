package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivitySplash extends AppCompatActivity {
    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        int totalProgressTime = 10000; // thời gian tối đa (ms)
        int intervalTime = 10; // thời gian giữa mỗi lần cập nhật (ms)
        int increment = 1; // giá trị tăng của ProgressBar

        final Handler handler = new Handler();
        getPermission();
        Runnable runnable = new Runnable() {
            int progress = 0;


            public void run() {
                progressBar.setProgress(progress);
                if (progress == progressBar.getMax()) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                    if (currentUser != null) {
                        // User is already logged in, navigate to MainActivity
                        startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
                        finish(); // Optional: Close the current activity
                    } else {
                        // User is not logged in, navigate to LoginActivity
                        startActivity(new Intent(ActivitySplash.this, ActivityLogIn.class));
                        finish(); // Optional: Close the current activity
                    }
                } else {
                    progress += increment;
                    handler.postDelayed(this, intervalTime);
                }
            }
        };
        handler.postDelayed(runnable, intervalTime);
    }
    private void getPermission() {
        if(ContextCompat.checkSelfPermission(ActivitySplash.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        }else {
            askPermission();
        }
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(ActivitySplash.this , new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(ActivitySplash.this, "Please provide the required permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
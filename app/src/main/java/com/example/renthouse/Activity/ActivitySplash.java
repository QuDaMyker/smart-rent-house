package com.example.renthouse.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivitySplash extends AppCompatActivity {
    private final static int REQUEST_CODE = 100;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getPermission();

    }



    private void runningSplash() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        int totalProgressTime = 10000; // Total progress time in milliseconds
        int intervalTime = 1; // Interval time for each update in milliseconds
        int increment = 10; // Increment value of the progress bar

        final int maxProgress = totalProgressTime / intervalTime; // Calculate the maximum progress value
        progressBar.setMax(maxProgress);

        new Thread(new Runnable() {
            public void run() {
                int progress = 0;

                while (progress < maxProgress) {
                    try {
                        Thread.sleep(intervalTime);
                        progress += increment;

                        // Update the progress bar on the UI thread
                        int finalProgress = progress;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressBar.setProgress(finalProgress);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                preferenceManager = new PreferenceManager(getApplicationContext());
                if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN) == true) {
                    startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
                } else {
                    startActivity(new Intent(ActivitySplash.this, ActivityLogIn.class));
                }
                finish();

                // After the progress is complete, check the user authentication status
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                // Start the appropriate activity based on the user's authentication status
                runOnUiThread(new Runnable() {
                    public void run() {
                        /*if (currentUser != null) {
                            // User is already logged in, navigate to MainActivity
                            startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
                        } else {
                            // User is not logged in, navigate to LoginActivity
                            startActivity(new Intent(ActivitySplash.this, ActivityLogIn.class));
                        }

                        finish(); // Optional: Close the current activity*/

                    }
                });
            }
        }).start();
    }


    private void getPermission() {
        if(ContextCompat.checkSelfPermission(ActivitySplash.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            runningSplash();
        }else {
            String[] permissons = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissons, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                runningSplash();
            } else {
                finish();
            }
        }
    }
}
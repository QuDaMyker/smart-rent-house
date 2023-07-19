package com.example.renthouse.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.renthouse.Admin.Activity.Admin_ActivityMain;
import com.example.renthouse.OnBoard.ActivityOnBoard;
import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivitySplash extends AppCompatActivity {
    private final static int REQUEST_CODE = 100;
    private PreferenceManager preferenceManager;
    private boolean isCompleteAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

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

                if (!preferenceManager.getBoolean(Constants.KEY_FIRST_INSTALL)) {
                    startActivity(new Intent(ActivitySplash.this, ActivityOnBoard.class));
                    finish();
                } else {
                    if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
                        Log.d("admin", preferenceManager.getString(Constants.KEY_EMAIL) + "");
                        if (preferenceManager.getString(Constants.KEY_EMAIL).equals("admin")) {
                            startActivity(new Intent(ActivitySplash.this, Admin_ActivityMain.class));
                        } else {
                            putDataAccess();
                            startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
                        }
                    } else {
                        Log.d("admin", "khong dang nhap");
                        putDataAccess();
                        startActivity(new Intent(ActivitySplash.this, ActivityLogIn.class));
                    }

                    finish();
                }


            }
        }).start();

//
    }

    private void putDataAccess() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference accessRef = database.getReference("Access");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());

        // Tăng số lần truy cập cho ngày hiện tại
        DatabaseReference currentDateRef = accessRef.child(currentDate);
        currentDateRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer accessCount = mutableData.getValue(Integer.class);
                if (accessCount == null) {
                    accessCount = 1;
                } else {
                    accessCount++;
                }
                mutableData.setValue(accessCount);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    // Xử lý lỗi nếu cần
                } else {
                    // Cập nhật số lần truy cập thành công
                }
            }
        });
    }


    private void getPermission() {
        if (ContextCompat.checkSelfPermission(ActivitySplash.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            runningSplash();
        } else {
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

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}
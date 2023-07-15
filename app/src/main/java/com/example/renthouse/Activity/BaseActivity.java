package com.example.renthouse.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.renthouse.BroadcastReceiver.InternetBroadcastReceiver;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

public class BaseActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private InternetBroadcastReceiver receiver;
    private ProgressDialog progressDialogInternetChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        preferenceManager = new PreferenceManager(getApplicationContext());
        loadDataUser();

        progressDialogInternetChange = new ProgressDialog(this);
        progressDialogInternetChange.setCancelable(false);
        progressDialogInternetChange.setMessage("Disconnected Internet");


        receiver = new InternetBroadcastReceiver(progressDialogInternetChange);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);


        registerReceiver(receiver, intentFilter);

    }

    private void loadDataUser() {
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            DatabaseReference blockedRef = reference.child("Accounts").child(preferenceManager.getString(Constants.KEY_USER_KEY)).child("blocked");
            blockedRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        boolean isBlocked = snapshot.getValue(Boolean.class);
                        if (isBlocked) {
                            preferenceManager.putBoolean(Constants.KEY_IS_BLOCKED, true);
                            startActivity(new Intent(getApplicationContext(), ActivityBlocked.class));
                        } else {
                            //startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        loadDataUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void showProgressDialog() {
        if (progressDialogInternetChange != null && !progressDialogInternetChange.isShowing()) {
            progressDialogInternetChange.show();
        }
    }

    public void dismissProgressDialog() {
        if (progressDialogInternetChange != null && progressDialogInternetChange.isShowing()) {
            progressDialogInternetChange.dismiss();
        }
    }
}
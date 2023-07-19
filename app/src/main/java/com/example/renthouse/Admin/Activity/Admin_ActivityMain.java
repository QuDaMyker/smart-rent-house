package com.example.renthouse.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.renthouse.Activity.ActivityMain;
import com.example.renthouse.Admin.Fragment.Admin_FragmentHome;
import com.example.renthouse.Admin.Fragment.Admin_FragmentNguoiDung;
import com.example.renthouse.Admin.Fragment.Admin_FragmentPhongTro;
import com.example.renthouse.BroadcastReceiver.InternetBroadcastReceiver;
import com.example.renthouse.Fragment.FragmentHome;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityAdminMainBinding;

public class Admin_ActivityMain extends AppCompatActivity implements DialogListener {
    private ActivityAdminMainBinding binding;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogInternetChange;
    private InternetBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getColor(R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setDefaultFragment();

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.btnHome: {
                    replaceFragment(new Admin_FragmentHome());
                    break;
                }
                case R.id.btnDSPhongTro: {
                    replaceFragment(new Admin_FragmentPhongTro());
                    break;
                }
                case R.id.btnUser: {
                    replaceFragment(new Admin_FragmentNguoiDung());
                    break;
                }
            }
            return true;
        });
        progressDialogInternetChange = new ProgressDialog(this);
        progressDialogInternetChange.setCancelable(false);
        progressDialogInternetChange.setMessage("Disconnected Internet");


        progressDialog = new ProgressDialog(Admin_ActivityMain.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        receiver = new InternetBroadcastReceiver(progressDialogInternetChange);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);


        registerReceiver(receiver, intentFilter);
    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frame_layout, new Admin_FragmentHome());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
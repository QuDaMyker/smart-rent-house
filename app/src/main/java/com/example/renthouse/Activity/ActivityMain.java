package com.example.renthouse.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.renthouse.FCM.FCMSend;
import com.example.renthouse.FCM.TokenUpdateTask;
import com.example.renthouse.Fragment.FragmentAccount;
import com.example.renthouse.Fragment.FragmentChat;
import com.example.renthouse.Fragment.FragmentHome;
import com.example.renthouse.Fragment.FragmentLiked;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.Interface.OnActivityResultListener;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Device;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import io.reactivex.annotations.NonNull;

public class ActivityMain extends BaseActivity implements DialogListener {
    private ActivityMainBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getColor(R.color.Primary_40));

        setDefaultFragment();

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.btnHome) {
                replaceFragment(new FragmentHome());
            } else if (item.getItemId() == R.id.btnLiked) {
                replaceFragment(new FragmentLiked());
            } else if (item.getItemId() == R.id.btnChat) {
                replaceFragment(new FragmentChat());
            } else if (item.getItemId() == R.id.btnAccount) {
                replaceFragment(new FragmentAccount());
            }
            return true;
        });

        TokenUpdateTask task = new TokenUpdateTask();
        task.execute();


        progressDialog = new ProgressDialog(ActivityMain.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

    }


    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new FragmentHome());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
}
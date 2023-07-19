package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Reports;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityContactAdminBinding;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityContactAdmin extends AppCompatActivity {
    private ActivityContactAdminBinding binding;
    private PreferenceManager preferenceManager;
    private AccountClass account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactAdminBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_contact_admin);
        setContentView(binding.getRoot());
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        loadIntent();
        setListeners();
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v-> onBackPressed());
        binding.btnSent.setOnClickListener(v->{
            if(checkValid()) {
                Intent intent = getIntent();
                AccountClass account = (AccountClass) intent.getSerializableExtra("account");
                String keyUser = intent.getStringExtra("key");
                String email = account.getEmail();
                String title = binding.txtEdtTieuDe.getText().toString().trim();
                String content = binding.txtEdtMoTaSuCo.getText().toString().trim();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(now);
                Reports reports = new Reports(account, keyUser, title, content, formattedDate);

                reference.child("Reports").push().setValue(reports);

                // go bo thong tin dang nhap va clear SharedPrefence
                clearCacheAccount();
                startActivity(new Intent(ActivityContactAdmin.this, ActivityLogIn.class));
                finish();

            }
        });
    }

    private void loadIntent() {
        Intent intent = getIntent();
        AccountClass account = (AccountClass) intent.getSerializableExtra("account");
        binding.nameUser.setText(account.getFullname());
        binding.emailUser.setText(account.getEmail());
        if(account.getBlocked()) {
            binding.blockUser.setText("Đã khóa");
        }
    }
    private Boolean checkValid() {
        if(binding.txtEdtTieuDe.getText().toString().isEmpty()) {
            binding.txtInTieuDe.setError("Vui lòng điền tiêu đề");
            return false;
        }
        if(binding.txtEdtMoTaSuCo.getText().toString().isEmpty()) {
            binding.txtInMoTaSuCo.setError("Vui lòng điền mô tả sự cố");
            return false;
        }
        return true;
    }
    private void clearCacheAccount(){
        preferenceManager = new PreferenceManager(ActivityContactAdmin.this);

        preferenceManager.clear();
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(ActivityContactAdmin.this, gso);
        googleSignInClient.signOut();
    }
}
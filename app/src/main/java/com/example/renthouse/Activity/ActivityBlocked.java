package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityBlockedBinding;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityBlocked extends AppCompatActivity {
    public ActivityBlockedBinding binding;
    public PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlockedBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_blocked);
        setContentView(binding.getRoot());

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getColor(R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setListeners();
        
    }

    public void setListeners() {
        binding.backToLoginBtn.setOnClickListener(v-> {
            // go bo thong tin dang nhap va clear SharedPrefence
            clearCacheAccount();
            startActivity(new Intent(ActivityBlocked.this, ActivityLogIn.class));
            finish();
            //onBackPressed();
        });
        binding.contactBtn.setOnClickListener(v->{
            sendReport();
        });
    }

    public Object sendReport() {
        Intent intent = new Intent(ActivityBlocked.this, ActivityContactAdmin.class);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Accounts");
        Query queryEmail = reference.orderByChild("email").equalTo(currentUser.getEmail());
        queryEmail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AccountClass account = snapshot.getValue(AccountClass.class);
                        intent.putExtra("key", snapshot.getKey());
                        intent.putExtra("account", account);
                        Toast.makeText(getApplicationContext(), account.getFullname(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // go bo thong tin dang nhap va clear SharedPrefence
        clearCacheAccount();
        return null;
    }

    public void clearCacheAccount(){
        preferenceManager = new PreferenceManager(ActivityBlocked.this);

        preferenceManager.clear();
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(ActivityBlocked.this, gso);
        googleSignInClient.signOut();
    }
}
package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private ActivityBlockedBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlockedBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_blocked);
        setContentView(binding.getRoot());


        setListeners();
        
    }

    private void setListeners() {
        binding.backToLoginBtn.setOnClickListener(v-> {
            // go bo thong tin dang nhap va clear SharedPrefence
            clearCacheAccount();
            onBackPressed();
        });
        binding.contactBtn.setOnClickListener(v->{
            sendReport();
        });
    }

    private void sendReport() {
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
                        intent.putExtra("fullname", account.getFullname());
                        intent.putExtra("email", account.getEmail());
                        intent.putExtra("blocked", account.getBlocked());
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
    }

    private void clearCacheAccount(){
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
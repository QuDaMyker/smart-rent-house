package com.example.renthouse.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityAccountPersonalBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivityAccount extends Activity {
    private ActivityAccountPersonalBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountPersonalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        getCurrentUser();

        binding.accountPersonalInfomationButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getCurrentUser() {
        if (currentUser != null) {
            String providerId = null;
            String displayName = null;
            String email = null;
            String photoUrl = null;
            List<? extends UserInfo> userInfoList = currentUser.getProviderData();

            for (UserInfo userInfo : userInfoList) {
                providerId = userInfo.getProviderId();
                displayName = userInfo.getDisplayName();
                email = userInfo.getEmail();
                photoUrl = userInfo.getPhotoUrl().toString();
            }
            binding.accountPersonalEmailProfile.setText(email);
            binding.accountPersonalNameProfile.setText(displayName);
            Picasso.get()
                    .load(photoUrl)
                    .placeholder(R.drawable.ic_avatar_account)
                    .error(R.drawable.back_ground_background)
                    .into(binding.accountPersonalImageProfile);
        }
    }
}

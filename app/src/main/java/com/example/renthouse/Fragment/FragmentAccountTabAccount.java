package com.example.renthouse.Fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.renthouse.Activity.ActivityAccountNotification;
import com.example.renthouse.Activity.ActivityDetailAccount;
import com.example.renthouse.Activity.ActivityMain;
import com.example.renthouse.Activity.ActivityReportError;
import com.example.renthouse.Activity.ActivitySplash;
import com.example.renthouse.Interface.Callback;
import com.example.renthouse.Interface.OnActivityResultListener;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAccountTabAccountBinding;
import com.example.renthouse.utilities.CacheUtils;
import com.example.renthouse.utilities.Constants;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.app.ActivityManager;
import android.content.Context;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentAccountTabAccount extends Fragment {
    private FragmentAccountTabAccountBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressDialog progressDialog;
    private PreferenceManager preferenceManager;
    private OnActivityResultListener onActivityResultListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountTabAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        init();
        updateUI();
        setListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void init() {
        preferenceManager = new PreferenceManager(getActivity());
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    private void setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Thực hiện cập nhật dữ liệu ở đây
                updateUI();
                // Sau khi hoàn thành cập nhật, gọi phương thức setRefreshing(false) để kết thúc hiệu ứng làm mới
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        binding.accountPersonalInfomationButtonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ActivityDetailAccount.class);
            launcherDetailAccount.launch(intent);
        });

        binding.accountPersonalPolicyButtonProfile.setOnClickListener(v -> {

        });
        binding.accountPersonalNotificationButtonProfile.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ActivityAccountNotification.class));
        });
        binding.accountPersonalReportErrorButtonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ActivityReportError.class);
            startActivity(intent);
        });
        binding.accountPersonalLogoutButtonProfile.setOnClickListener(v -> {

            logOut();
        });
    }

    private void updateUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
                Picasso.get().load(preferenceManager.getString(Constants.KEY_IMAGE)).into(binding.accountPersonalImageProfile);
                binding.accountPersonalNameProfile.setText(preferenceManager.getString(Constants.KEY_FULLNAME));
                binding.accountPersonalEmailProfile.setText(preferenceManager.getString(Constants.KEY_EMAIL));
                progressDialog.dismiss();
            }
        });
    }

    private ActivityResultLauncher<Intent> launcherDetailAccount = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                updateUI();
            }
        }
    });

    public void setOnActivityResultListener(OnActivityResultListener listener) {
        this.onActivityResultListener = listener;
    }

    private void logOut() {
        preferenceManager.clear();

        preferenceManager.putBoolean(Constants.KEY_FIRST_INSTALL, true);

        Intent intent = new Intent(getContext(), ActivitySplash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        googleSignInClient.signOut();

        CacheUtils.clearCache(getContext());

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Log.d("status", "sign");
        } else {
            Log.d("status", "out");
        }
        startActivity(intent);
        getActivity().finish();
        /*getActivity().finish();
        getActivity().finishAffinity();*/
    }


}
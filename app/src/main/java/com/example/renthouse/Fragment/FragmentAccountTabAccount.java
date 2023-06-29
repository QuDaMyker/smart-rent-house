package com.example.renthouse.Fragment;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.example.renthouse.Activity.ActivityReportError;
import com.example.renthouse.Activity.ActivitySplash;
import com.example.renthouse.Interface.Callback;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
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


public class FragmentAccountTabAccount extends Fragment {
    ImageView imageProfile;
    TextView nameProfile;
    TextView emailProfile;
    Button btnThongTinCanhan;
    Button btnDieuKhoanChinhSach;
    Button btnThongBao;
    Button btnBaoCaoSuCo;
    Button btnDangXuat;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String key;
    AccountClass account;
    ProgressDialog progressDialog;
    private PreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_tab_account, container, false);


        imageProfile = view.findViewById(R.id.account_personal_imageProfile);
        nameProfile = view.findViewById(R.id.account_personal_nameProfile);
        emailProfile = view.findViewById(R.id.account_personal_emailProfile);
        btnThongTinCanhan = view.findViewById(R.id.account_personal_infomationButtonProfile);
        btnDieuKhoanChinhSach = view.findViewById(R.id.account_personal_policyButtonProfile);
        btnThongBao = view.findViewById(R.id.account_personal_notificationButtonProfile);
        btnBaoCaoSuCo = view.findViewById(R.id.account_personal_reportErrorButtonProfile);
        btnDangXuat = view.findViewById(R.id.account_personal_logoutButtonProfile);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        if (currentUser != null) {
            progressDialog.show();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Accounts");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        key = dataSnapshot.getKey();
                        if (dataSnapshot.child("email").getValue().equals(currentUser.getEmail())) {
                            account = dataSnapshot.getValue(AccountClass.class);
                            break;
                        }
                    }

                    nameProfile.setText(account.getFullname());
                    emailProfile.setText(account.getEmail());
                    Picasso.get().load(account.getImage()).into(imageProfile);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        btnThongTinCanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityDetailAccount.class);
                intent.putExtra("ACCOUNT", account);
                intent.putExtra("KEY", key);
                startActivity(intent);
            }
        });



        btnBaoCaoSuCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityReportError.class);
                startActivity(intent);
            }
        });

        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityAccountNotification.class));
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager = new PreferenceManager(getActivity());

                preferenceManager.clear();
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                googleSignInClient.signOut();
                getActivity().finish();
                startActivity(new Intent(requireContext(), ActivitySplash.class));


            }
        });


        return view;
    }
}
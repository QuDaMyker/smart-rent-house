package com.example.renthouse.Fragment;

import android.content.Intent;
import android.net.Uri;
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

import com.example.renthouse.Activity.ActivityDetailAccount;
import com.example.renthouse.Activity.ActivityReportError;
import com.example.renthouse.Activity.ActivitySplash;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
    private ProgressBar progressBar;
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

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Accounts");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        key = dataSnapshot.getKey();
                        if(dataSnapshot.child("email").getValue().equals(currentUser.getEmail())) {
                            account = dataSnapshot.getValue(AccountClass.class);
                            break;
                        }
                    }

                    nameProfile.setText(account.getFullname());
                    emailProfile.setText(account.getEmail());
                    Picasso.get().load(account.getImage()).into(imageProfile);
                    progressBar.setVisibility(View.INVISIBLE);
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

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getActivity()!= null) {
                    getActivity().finish();
                }
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), ActivitySplash.class));


            }
        });


        return view;
    }
}
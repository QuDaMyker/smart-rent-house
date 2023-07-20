package com.example.renthouse.Admin.Fragment_NguoiDung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renthouse.Admin.Activity.Admin_ActivityThongTinNguoiDung;
import com.example.renthouse.Admin.Adapter.NguoiDungAdapter;
import com.example.renthouse.Admin.Adapter.NguoiDungBiChanAdapter;
import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.Admin.listeners.ItemNguoiDungListener;
import com.example.renthouse.Admin.listeners.LoadDataFragment;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.databinding.FragmentAdminNguoiDungNguoiDungBiChanBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminNguoiDung_Fragment_NguoiDungBiChan extends Fragment implements ItemNguoiDungListener {
    private FragmentAdminNguoiDungNguoiDungBiChanBinding binding;
    private List<NguoiDung> nguoiDungs;
    private List<NguoiDung> tempNguoiDung;
    private PreferenceManager preferenceManager;
    private NguoiDungBiChanAdapter nguoiDungBiChanAdapter;
    private FirebaseDatabase database;
    private DialogListener dialogListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminNguoiDungNguoiDungBiChanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        init();
        //loadData();
        setListeners();
        return view;
    }

    @Override
    public void onResume() {
        nguoiDungs.clear();
        tempNguoiDung.clear();
        loadData();
        super.onResume();

    }

    private void setListeners() {
       /* binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });*/
    }


    private void init() {
        nguoiDungs = new ArrayList<>();
        tempNguoiDung = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        preferenceManager = new PreferenceManager(getContext());

        nguoiDungBiChanAdapter = new NguoiDungBiChanAdapter(getContext(), nguoiDungs, this);
        binding.recycleView.setAdapter(nguoiDungBiChanAdapter);
        database = FirebaseDatabase.getInstance();
    }

    private void loadData() {
        dialogListener.showDialog();

        nguoiDungs.clear();
        tempNguoiDung.clear();

        DatabaseReference reference = database.getReference();
        Query query = reference.child("Accounts").orderByChild("blocked").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<NguoiDung> tempNguoiDungs = new ArrayList<>();
                    int dataSnapshotCount = (int) dataSnapshot.getChildrenCount();
                    AtomicInteger count = new AtomicInteger(0);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        AccountClass account = snapshot.getValue(AccountClass.class);
                        Query query1 = reference.child("Rooms").orderByChild("createdBy/email").equalTo(account.getEmail());
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int countRoom = (int) snapshot.getChildrenCount();
                                tempNguoiDungs.add(new NguoiDung(key, account, countRoom));

                                if (count.incrementAndGet() == dataSnapshotCount) {
                                    nguoiDungs.addAll(tempNguoiDungs);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            nguoiDungBiChanAdapter.notifyDataSetChanged();
                                            dialogListener.dismissDialog();
                                        }
                                    });
                                    dialogListener.dismissDialog();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialogListener.dismissDialog();
                            }
                        });
                    }
                    dialogListener.dismissDialog();
                    binding.animationView.setVisibility(View.INVISIBLE);
                    binding.recycleView.setVisibility(View.VISIBLE);
                } else {
                    binding.animationView.setVisibility(View.VISIBLE);
                    binding.recycleView.setVisibility(View.INVISIBLE);
                    dialogListener.dismissDialog();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
                dialogListener.dismissDialog();
            }
        });
    }

    @Override
    public void onItemNguoiDungClick(NguoiDung nguoiDung) {
        Intent intent = new Intent(getActivity(), Admin_ActivityThongTinNguoiDung.class);
        intent.putExtra(Constants.KEY_NGUOIDUNG, nguoiDung);
        mStartForResult.launch(intent);
    }

    private ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                // Handle the Intent
                nguoiDungs.clear();
                nguoiDungBiChanAdapter.notifyDataSetChanged();
                loadData();
            }
        }
    });


}
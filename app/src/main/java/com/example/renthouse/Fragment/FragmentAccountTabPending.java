package com.example.renthouse.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Adapter.PostedAdapter;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAccountPostedBinding;
import com.example.renthouse.databinding.FragmentAccountTabPendingBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentAccountTabPending extends Fragment {
    private FragmentAccountTabPendingBinding binding;
    private List<Room> listRoomPosted;
    private List<Room> tempRoom;
    private PostedAdapter postedAdapter;
    private PreferenceManager preferenceManager;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountTabPendingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        init();
        loadData();
        setListeners();


        return view;
    }

    private void init() {
        preferenceManager = new PreferenceManager(getContext());

        listRoomPosted = new ArrayList<>();
        tempRoom = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        postedAdapter = new PostedAdapter(getContext(), listRoomPosted, new ItemClick() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(getContext(), ActivityDetails.class);
                intent.putExtra("selectedRoom", room);
                startActivity(intent);
            }
        });

        binding.recycleView.setLayoutManager(gridLayoutManager);
        binding.recycleView.setAdapter(postedAdapter);
    }

    private void loadData() {
        dialogListener.showDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        listRoomPosted.clear();
        Query query = reference.child("Rooms").orderByChild("createdBy/email").equalTo(preferenceManager.getString(Constants.KEY_EMAIL));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tempRoom.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Room room = snapshot.getValue(Room.class);
                        if (room.getStatus().equals("pending")) {
                            tempRoom.add(room);
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listRoomPosted.addAll(tempRoom);
                            postedAdapter.notifyDataSetChanged();
                        }
                    });
                    if (listRoomPosted.isEmpty()) {
                        binding.recycleView.setVisibility(View.INVISIBLE);
                        binding.animationView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recycleView.setVisibility(View.VISIBLE);
                        binding.animationView.setVisibility(View.INVISIBLE);
                    }
                    dialogListener.dismissDialog();
                } else {
                    listRoomPosted.clear();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postedAdapter.notifyDataSetChanged();
                        }
                    });
                    if (listRoomPosted.isEmpty()) {
                        binding.recycleView.setVisibility(View.INVISIBLE);
                        binding.animationView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recycleView.setVisibility(View.VISIBLE);
                        binding.animationView.setVisibility(View.INVISIBLE);
                    }
                    dialogListener.dismissDialog();
                }

                dialogListener.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogListener.dismissDialog();
            }
        });
    }

    private void setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Thực hiện cập nhật dữ liệu ở đây
                loadData();
                // Sau khi hoàn thành cập nhật, gọi phương thức setRefreshing(false) để kết thúc hiệu ứng làm mới
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}
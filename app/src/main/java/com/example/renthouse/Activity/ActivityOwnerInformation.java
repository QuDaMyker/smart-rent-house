package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.Adapter.PostedAdapter;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityOwnerInformationBinding;
import com.example.renthouse.databinding.LayoutDialogCallBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityOwnerInformation extends AppCompatActivity {
    private ActivityOwnerInformationBinding binding;
    private Intent intent;
    private PreferenceManager preferenceManager;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<Room> listRoomPosted;
    private List<Room> tempRoom;
    private PostedAdapter postedAdapter;
    private ProgressDialog progressDialog;
    private Room roomIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnerInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getColor(R.color.Primary_40));

        init();
        updateUI();
        setListener();
        loadData();
    }

    private void init() {
        intent = getIntent();

        preferenceManager = new PreferenceManager(ActivityOwnerInformation.this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        progressDialog = new ProgressDialog(ActivityOwnerInformation.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        listRoomPosted = new ArrayList<>();
        tempRoom = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ActivityOwnerInformation.this, 2);

        postedAdapter = new PostedAdapter(ActivityOwnerInformation.this, listRoomPosted, new ItemClick() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(ActivityOwnerInformation.this, ActivityDetails.class);
                intent.putExtra("selectedRoom", room);
                startActivity(intent);
            }
        });

        binding.recycleView.setLayoutManager(gridLayoutManager);
        binding.recycleView.setAdapter(postedAdapter);

        roomIntent = (Room) intent.getSerializableExtra("roomToOwer");
    }

    private void updateUI() {
        binding.txtName.setText(roomIntent.getCreatedBy().getFullname());
        binding.txtPhoneNumber.setText(roomIntent.getPhoneNumber());
        Picasso.get().load(roomIntent.getCreatedBy().getImage()).into(binding.imageUser);
    }

    private void setListener() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.contactOwer.setOnClickListener(v -> {
            openFeedbackDialog(Gravity.CENTER, binding.txtPhoneNumber.getText().toString().trim());
            //showCallConfirmationDialog(binding.txtPhoneNumber.getText().toString().trim());
        });

    }

    private void loadData() {
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        listRoomPosted.clear();
        Query query = reference.child("Rooms").orderByChild("createdBy/email").equalTo(roomIntent.getCreatedBy().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tempRoom.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Room room = snapshot.getValue(Room.class);
                        if (room.getStatus().equals("approved")) {
                            tempRoom.add(room);
                        }
                    }
                    runOnUiThread(new Runnable() {
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
                    progressDialog.dismiss();
                } else {
                    listRoomPosted.clear();
                    runOnUiThread(new Runnable() {
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
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }

    private void showCallConfirmationDialog(String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận gọi số " + number);
                builder.setMessage("Bạn có chắc chắn muốn gọi số " + number + " không?");
        builder.setPositiveButton("Gọi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNumber = number;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void openFeedbackDialog(int gravity, String number) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_call);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        TextView txtNumber = dialog.findViewById(R.id.txtNumber);
        Button btnVerify = dialog.findViewById(R.id.btn_verify);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        txtNumber.setText("Xác nhận gọi cho số\n" + number);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = number;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
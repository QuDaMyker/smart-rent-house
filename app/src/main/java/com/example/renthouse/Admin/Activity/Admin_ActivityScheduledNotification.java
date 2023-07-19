package com.example.renthouse.Admin.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.renthouse.Adapter.UniAdapter;
import com.example.renthouse.Admin.Adapter.NotiScheduleAdapter;
import com.example.renthouse.Admin.OOP.NotiSchedule;
import com.example.renthouse.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_ActivityScheduledNotification extends AppCompatActivity {
    MaterialButton addBtn;
    RecyclerView scheduleRecycleView;
    NotiScheduleAdapter notiScheduleAdapter;
    List<NotiSchedule> notiScheduleList = new ArrayList<>();
    ActivityResultLauncher<Intent> launcher;

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scheduled_notification);

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getColor(R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        addBtn = findViewById(R.id.addBtn);
        scheduleRecycleView = findViewById(R.id.scheduleRecycleView);
        scheduleRecycleView.setLayoutManager(new LinearLayoutManager(this));
        notiScheduleAdapter = new NotiScheduleAdapter(notiScheduleList);

        btnBack = findViewById(R.id.btn_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        notiScheduleAdapter.setOnEditButtonClickListener(new NotiScheduleAdapter.OnEditButtonClickListener() {
//            @Override
//            public void onEditButtonClick(int position) {
//                Intent intent =new Intent(Admin_ActivityScheduledNotification.this, Admin_ActivityCreateSchedule.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("notiSchedule", notiScheduleList.get(position));
//                intent.putExtras(bundle);
//                launcher.launch(intent);
//            }
//        });
        notiScheduleAdapter.setOnDeleteButtonClickListener(new NotiScheduleAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int position) {
                AlertDialog confirmDialog = new AlertDialog.Builder(Admin_ActivityScheduledNotification.this)
                        .setTitle("Xóa lịch thông báo")
                        .setMessage("Bạn có chắc chắn muốn xóa lịch thông báo này?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference notiSchedulesRef = database.getReference("NotiSchedules");
                                notiSchedulesRef.child(notiScheduleList.get(position).getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Admin_ActivityScheduledNotification.this, "Successful", Toast.LENGTH_SHORT).show();
                                        loadList();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                confirmDialog.show();
            }
        });
        scheduleRecycleView.setAdapter(notiScheduleAdapter);
        loadList();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(new Intent(Admin_ActivityScheduledNotification.this, Admin_ActivityCreateSchedule.class));
            }
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            loadList();
                        }
                    }
                });
    }

    private void loadList(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang tải...");
        progressDialog.show();
        notiScheduleList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("NotiSchedules");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshots) {
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    NotiSchedule notiSchedule = snapshot.getValue(NotiSchedule.class);
                    notiScheduleList.add(notiSchedule);
                }
                notiScheduleAdapter.notifyDataSetChanged();
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
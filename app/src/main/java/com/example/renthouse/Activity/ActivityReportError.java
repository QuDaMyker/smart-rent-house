package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.renthouse.Interface.Callback;
import com.example.renthouse.Interface.ValueEventListenerCallback;
import com.example.renthouse.OOP.Reports;
import com.example.renthouse.Other.CommonUtils;
import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class ActivityReportError extends AppCompatActivity{
    private static final int EMAIL_SEND_REQUEST_CODE = 100;
    private TextInputLayout txtInTieuDe, txtInMoTa;
    private TextInputEditText txtEdtTieuDe, txtEdtMoTa;
    private ImageButton btnBack;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_error);

        txtInTieuDe = findViewById(R.id.txtInTieuDe);
        txtInMoTa = findViewById(R.id.txtInMoTaSuCo);

        txtEdtTieuDe = findViewById(R.id.txtEdtTieuDe);
        txtEdtMoTa = findViewById(R.id.txtEdtMoTaSuCo);

        btnBack = findViewById(R.id.btn_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSend = findViewById(R.id.btn_Sent);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                finish();
            }
        });
    }

    private void sendMail() {
        if (txtEdtTieuDe.getText().toString().isEmpty()) {
            txtInTieuDe.setError("Vui Lòng Điền Thông Tin");
        } else if (txtEdtMoTa.getText().toString().isEmpty()) {
            txtInMoTa.setError("Vui Lòng Điền Thông Tin");
        } else {
            // gui vao realtime
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            Reports reports = new Reports();
            reports.setTitle(txtEdtTieuDe.getText().toString().trim());
            reports.setContent(txtEdtMoTa.getText().toString().trim());
            reports.setEmail(currentUser.getEmail());
            Query query  =  reference.child("Accounts").orderByChild("email").equalTo(currentUser.getEmail());


            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        reports.setIdUser(dataSnapshot.getKey());
                    }
                    Callback<Void> callback = new Callback<Void>() {
                        @Override
                        public void onCallback(Void result) {
                            reference.child("Reports").push().setValue(reports);
                        }
                    };

                    callback.onCallback(null);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });













            String recipientList = "nthienphu163@gmail.com";
            String[] recipients = recipientList.split(",");

            String subject = txtEdtTieuDe.getText().toString();
            String message = txtEdtMoTa.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivityForResult(Intent.createChooser(intent, "Choose an email client"), EMAIL_SEND_REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EMAIL_SEND_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                CommonUtils.showNotification(ActivityReportError.this, "Cảm ơn", "Cảm ơn bạn đã báo cáo lỗi", R.drawable.ic_phobien_1);
                finish();
            } else {
                CommonUtils.showNotification(ActivityReportError.this, "Cảm ơn", "Cảm ơn bạn đã báo cáo lỗi", R.drawable.ic_phobien_1);

            }
        }
    }
}
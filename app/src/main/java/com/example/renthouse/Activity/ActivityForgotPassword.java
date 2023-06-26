package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgotPassword extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private ImageButton backToLoginBtn;
    private TextView tv_backToLoginBtn;
    private Button sentBtn;
    private TextInputEditText forgotPassword_email;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        progressDialog = new ProgressDialog(ActivityForgotPassword.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        forgotPassword_email = findViewById(R.id.forgotPassword_email);
        tv_backToLoginBtn = findViewById(R.id.tv_backToLoginBtn);
        sentBtn = findViewById(R.id.sentBtn);


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //
        //

        tv_backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //
        //

        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                FirebaseAuth.getInstance().sendPasswordResetEmail(forgotPassword_email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            // Gửi email thất bại
                            Toast.makeText(getApplicationContext(), "Gửi email đổi mật khẩu thất bại", Toast.LENGTH_LONG).show();
                        } else {
                            // Gửi email thành công
                            Toast.makeText(getApplicationContext(), "Email đổi mật khẩu đã được gửi đến " + forgotPassword_email.getText().toString().trim(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), ActivitySent.class);
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                        progressDialog.dismiss();
                    }
                });

            }
        });


    }
}
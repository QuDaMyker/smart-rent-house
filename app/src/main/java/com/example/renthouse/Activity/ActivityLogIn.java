package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.renthouse.R;

public class ActivityLogIn extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private TextView forgotPasswordBtn;
    private TextView signUpBtn;
    private Button loginWithGoogleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityForgotPassword.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitySignUp.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        loginWithGoogleBtn = findViewById(R.id.loginWithGoogleBtn);
        loginWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ActivitySearch.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // Xử lý kết quả trả về từ Activity B nếu cần
        }
    }

}
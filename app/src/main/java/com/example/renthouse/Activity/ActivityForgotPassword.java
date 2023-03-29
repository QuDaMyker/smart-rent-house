package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.renthouse.R;

public class ActivityForgotPassword extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private ImageButton backToLoginBtn;
    private TextView tv_backToLoginBtn;
    private Button sentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        //

        tv_backToLoginBtn = findViewById(R.id.tv_backToLoginBtn);
        tv_backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        //

        sentBtn = findViewById(R.id.sentBtn);
        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitySent.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


    }
}
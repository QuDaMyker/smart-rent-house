package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.renthouse.R;

public class ActivitySent extends AppCompatActivity {
    private ImageButton backToLoginBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);

        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
    }
}
package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.renthouse.R;

import org.w3c.dom.Text;

public class ActivitySignUp extends AppCompatActivity {
    private ImageButton backToLoginBtn;
    private Button signUpBtn;
    private TextView tv_backToLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signUpBtn = findViewById(R.id.signUpBtn);

        tv_backToLoginBtn = findViewById(R.id.tv_backToLoginBtn);
        tv_backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        //
    }
}
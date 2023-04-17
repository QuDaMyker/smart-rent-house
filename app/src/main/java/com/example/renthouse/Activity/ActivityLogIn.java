package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogIn extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private TextView forgotPasswordBtn;
    private TextView signUpBtn;
    private Button loginWithGoogleBtn, login_logInBtn;
    FirebaseAuth mAuth;
    private TextInputEditText login_email, login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        signUpBtn = findViewById(R.id.login_signUpBtn);
        loginWithGoogleBtn = findViewById(R.id.login_loginWithGoogleBtn);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_logInBtn = findViewById(R.id.login_logInBtn);
        mAuth = FirebaseAuth.getInstance();

        login_logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(login_email.getText());
                password = String.valueOf(login_password.getText());

                if (TextUtils.isEmpty(email)) {
                    login_email.setError("Vui lòng điền email");
                    return;
                }
                if(!isValidEmail(email)) {
                    login_email.setError("Vui lòng điền đúng định dạng email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    login_password.setError("Vui lòng điền mật khẩu");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ActivityChat.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ActivityLogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityForgotPassword.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitySignUp.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
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

    public boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
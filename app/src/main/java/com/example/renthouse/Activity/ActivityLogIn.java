package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.TreeMap;

public class ActivityLogIn extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private TextView forgotPasswordBtn;
    private TextView signUpBtn;
    private Button loginWithGoogleBtn, login_logInBtn;
    FirebaseAuth mAuth;
    private TextInputEditText login_email, login_password;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

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
                if (!isValidEmail(email)) {
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
                        if (task.isSuccessful()) {
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
        // login with google

        loginWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                gsc = GoogleSignIn.getClient(ActivityLogIn.this, gso);
                signInWithGoogle();

            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                firebaseAuthWithGoogle(account.getIdToken());
                if (account != null) {
                    String personName = account.getDisplayName();
                    String personEmail = account.getEmail();
                    String personId = account.getId();
                    Uri personPhoto = account.getPhotoUrl();

                    Toast.makeText(getApplicationContext(), personName, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), personEmail, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), personId, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), personPhoto.toString(), Toast.LENGTH_SHORT).show();
                }
                //
                //
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(ActivityLogIn.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String TAG;
                        if (task.isSuccessful()) {
                            // Cập nhật thông tin người dùng lên Firebase Authentication
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUserInfo(user);
                            // Đăng nhập thành công
                            Toast.makeText(getApplicationContext(), "Dang Nhap Thanh Cong", Toast.LENGTH_SHORT).show();
                        } else {
                            // Đăng nhập thất bại
                            Toast.makeText(getApplicationContext(), "That bai", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void updateUserInfo(FirebaseUser user) {
        // Cập nhật thông tin người dùng lên Firebase Authentication
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getDisplayName())
                .setPhotoUri(user.getPhotoUrl())
                .build();
        user.updateProfile(profileUpdates);
    }
    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityChat.class);
        startActivity(intent);
    }

    public boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
package com.example.renthouse.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.renthouse.Admin.Activity.Admin_ActivityMain;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.Other.CommonUtils;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityLogInBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class ActivityLogIn extends AppCompatActivity {
    private ActivityLogInBinding binding;
    private static final int REQUEST_CODE = 1;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private PreferenceManager preferenceManager;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
    }

    private void init() {
        preferenceManager = new PreferenceManager(getApplicationContext());

        progressDialog = new ProgressDialog(ActivityLogIn.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        storage = FirebaseStorage.getInstance();

        binding.inputEmail.setSingleLine();
        binding.inputPassword.setSingleLine();

        if (binding.inputPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
            // Mã hóa mật khẩu
            binding.inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // Không mã hóa mật khẩu
            binding.inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }

    private void setListeners() {
        binding.inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.outlineEmail.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.outlinePassword.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.loginLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.inputEmail.getText().toString().trim().equals("admin") && binding.inputPassword.getText().toString().trim().equals("admin")) {
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_EMAIL, binding.inputEmail.getText().toString().trim());
                    preferenceManager.putString(Constants.KEY_PASSWORD, binding.inputPassword.getText().toString().trim());
                    CommonUtils.showNotification(ActivityLogIn.this, "Thông Báo", "Chào mừng Admin trở lại", R.drawable.ic_phobien_1);
                    startActivity(new Intent(ActivityLogIn.this, Admin_ActivityMain.class));
                    finish();
                    return;
                }

                if (checkCondition()) {
                    logInWithEmailPassword();
                }

            }
        });
        binding.forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityForgotPassword.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        binding.loginSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitySignUp.class);
                startActivity(intent);
            }
        });
        binding.loginLoginWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
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

    private void logInWithEmailPassword() {
        progressDialog.show();
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.show();
                    preferenceManager.putString(Constants.KEY_EMAIL, email);
                    preferenceManager.putString(Constants.KEY_PASSWORD, password);
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    DatabaseReference ref = database.getReference();
                    Query query = ref.child("Accounts").orderByChild("email").equalTo(email);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            AccountClass account = null;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                preferenceManager.putString(Constants.KEY_USER_KEY, dataSnapshot.getKey());
                                account = dataSnapshot.getValue(AccountClass.class);
                            }

                            preferenceManager.putString(Constants.KEY_FULLNAME, account.getFullname());
                            preferenceManager.putString(Constants.KEY_IMAGE, account.getImage());
                            preferenceManager.putString(Constants.KEY_PHONENUMBER, account.getPhoneNumber());
                            preferenceManager.putString(Constants.KEY_DATECREATEDACCOUNT, account.getNgayTaoTaiKhoan());

                            ref.child("Accounts").child(preferenceManager.getString(Constants.KEY_USER_KEY)).child("password").setValue(password);


                            CommonUtils.showNotification(getApplicationContext(), "Thông báo", "Chào mừng bạn trở lại, hãy lựa chọn căn phòng ưng ý mình nhé", R.drawable.ic_phobien_1);
                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                            Toast.makeText(ActivityLogIn.this, "Có lỗi xảy ra vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(ActivityLogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ActivityLogIn.this, "Sai tài khoản hoặc mật khẩu vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        progressDialog.dismiss();
    }

    private void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        //startActivityForResult(signInIntent, 100);
        callIntentGoogle.launch(signInIntent);
    }

    private ActivityResultLauncher<Intent> callIntentGoogle = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                progressDialog.show();
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                if (signInAccountTask.isSuccessful()) {
                    Log.d("status", "Receive data from GG successfully");
                    try {
                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                        if (googleSignInAccount != null) {
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                            mAuth.signInWithCredential(authCredential).addOnCompleteListener(ActivityLogIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.show();
                                        // Cập nhật thông tin người dùng lên Firebase Authentication
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUserInfo(user);
                                        // them thong tin tai khoan vao realtime
                                        if (user != null) {
                                            String personalID = user.getUid();
                                            String personName = user.getDisplayName();
                                            String personEmail = user.getEmail();
                                            Uri personPhoto = user.getPhotoUrl();

                                            Date now = new Date();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String formattedDate = dateFormat.format(now);
                                            AccountClass accountClass = new AccountClass(personName, personEmail, "+84", "********", personPhoto.toString(), formattedDate, false, null);
                                            String emailToCheck = personEmail;

                                            preferenceManager.putString(Constants.KEY_IMAGE, accountClass.getImage());
                                            preferenceManager.putString(Constants.KEY_PHONENUMBER, accountClass.getPhoneNumber());
                                            preferenceManager.putString(Constants.KEY_EMAIL, accountClass.getEmail());
                                            preferenceManager.putString(Constants.KEY_FULLNAME, accountClass.getFullname());
                                            preferenceManager.putString(Constants.KEY_DATECREATEDACCOUNT, accountClass.getNgayTaoTaiKhoan());

                                            DatabaseReference accountsRef = reference.child("Accounts");
                                            Query emailQuery = accountsRef.orderByChild("email").equalTo(emailToCheck);

                                            emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (!dataSnapshot.exists()) {
                                                        DatabaseReference newChildRef = reference.child("Accounts").push();
                                                        String generatedKey = newChildRef.getKey();
                                                        preferenceManager.putString(Constants.KEY_USER_KEY, generatedKey);

                                                        newChildRef.setValue(accountClass)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            // Data successfully saved

                                                                            CommonUtils.showNotification(getApplicationContext(), "Thông báo", "Chào mừng bạn đến với chúng tôi, hãy lựa chọn căn phòng ưng ý mình nhé", R.drawable.ic_phobien_1);
                                                                            progressDialog.dismiss();
                                                                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                                            startActivity(new Intent(ActivityLogIn.this, ActivityMain.class));
                                                                        } else {
                                                                            // Handle the error here
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            AccountClass checkAcc = snapshot.getValue(AccountClass.class);
                                                            preferenceManager.putString(Constants.KEY_USER_KEY, snapshot.getKey());
                                                            preferenceManager.putString(Constants.KEY_IMAGE, checkAcc.getImage());
                                                            preferenceManager.putBoolean(Constants.KEY_IS_BLOCKED, checkAcc.getBlocked());
                                                            if (checkAcc.getBlocked()) {
                                                                progressDialog.dismiss();
                                                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);

                                                                startActivity(new Intent(ActivityLogIn.this, ActivityBlocked.class));
                                                            } else {
                                                                CommonUtils.showNotification(getApplicationContext(), "Thông báo", "Chào mừng bạn trở lại, hãy lựa chọn căn phòng ưng ý mình nhé", R.drawable.ic_phobien_1);
                                                                progressDialog.dismiss();
                                                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                                startActivity(new Intent(ActivityLogIn.this, ActivityMain.class));
                                                            }
                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Handle the error here
                                                    CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Thất bại", R.drawable.ic_phobien_1);
                                                    progressDialog.dismiss();
                                                }
                                            });
                                        }
                                        //pushSuccessFullNotification();
                                        CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Chào mừng bạn trở lại", R.drawable.ic_phobien_1);
                                        //Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Đăng nhập thất bại
                                        //pushFailerNotification();
                                        CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Thất bại", R.drawable.ic_phobien_1);
                                        //Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                progressDialog.show();
                task.getResult(ApiException.class);
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                firebaseAuthWithGoogle(account.getIdToken());


            } catch (ApiException e) {
                Toast.makeText(ActivityLogIn.this, "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        } else {
            progressDialog.dismiss();
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String TAG;
                        if (task.isSuccessful()) {
                            progressDialog.show();
                            // Cập nhật thông tin người dùng lên Firebase Authentication
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUserInfo(user);
                            // them thong tin tai khoan vao realtime
                            if (user != null) {
                                String personalID = user.getUid();
                                String personName = user.getDisplayName();
                                String personEmail = user.getEmail();
                                Uri personPhoto = user.getPhotoUrl();

                                Date now = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = dateFormat.format(now);
                                AccountClass accountClass = new AccountClass(personName, personEmail, "+84", "********", personPhoto.toString(), formattedDate, false, null);
                                String emailToCheck = personEmail;

                                preferenceManager.putString(Constants.KEY_IMAGE, accountClass.getImage());
                                preferenceManager.putString(Constants.KEY_PHONENUMBER, accountClass.getPhoneNumber());
                                preferenceManager.putString(Constants.KEY_EMAIL, accountClass.getEmail());
                                preferenceManager.putString(Constants.KEY_FULLNAME, accountClass.getFullname());
                                preferenceManager.putString(Constants.KEY_DATECREATEDACCOUNT, accountClass.getNgayTaoTaiKhoan());

                                DatabaseReference accountsRef = reference.child("Accounts");
                                Query emailQuery = accountsRef.orderByChild("email").equalTo(emailToCheck);

                                emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (!dataSnapshot.exists()) {
                                            DatabaseReference newChildRef = reference.child("Accounts").push();
                                            String generatedKey = newChildRef.getKey();
                                            preferenceManager.putString(Constants.KEY_USER_KEY, generatedKey);

                                            newChildRef.setValue(accountClass)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // Data successfully saved

                                                                //pushSuccessFullNotification();
                                                                progressDialog.dismiss();
                                                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                                startActivity(new Intent(ActivityLogIn.this, ActivityMain.class));
                                                            } else {
                                                                // Handle the error here
                                                            }
                                                        }
                                                    });
                                        } else {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                AccountClass checkAcc = snapshot.getValue(AccountClass.class);
                                                preferenceManager.putString(Constants.KEY_USER_KEY, snapshot.getKey());
                                                preferenceManager.putString(Constants.KEY_IMAGE, checkAcc.getImage());

                                                if (checkAcc.getBlocked()) {
                                                    progressDialog.dismiss();
                                                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);

                                                    startActivity(new Intent(ActivityLogIn.this, ActivityBlocked.class));
                                                } else {
                                                    //pushSuccessFullNotification();
                                                    progressDialog.dismiss();
                                                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                    startActivity(new Intent(ActivityLogIn.this, ActivityMain.class));
                                                }
                                            }

                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle the error here
                                        CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Thất bại", R.drawable.ic_phobien_1);
                                        progressDialog.dismiss();
                                    }
                                });

                            }

                            //pushSuccessFullNotification();
                            CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Chào mừng bạn trở lại", R.drawable.ic_phobien_1);
                            //Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Đăng nhập thất bại
                            //pushFailerNotification();
                            CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Thất bại", R.drawable.ic_phobien_1);
                            //Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        progressDialog.dismiss();
    }

    private void updateUserInfo(FirebaseUser user) {
        // Cập nhật thông tin người dùng lên Firebase Authentication
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getDisplayName())
                .setPhotoUri(user.getPhotoUrl())
                .build();
        user.updateProfile(profileUpdates);
    }

    private boolean checkCondition() {
        boolean flag = true;
        if (binding.inputEmail.getText().toString().isEmpty()) {
            flag = false;
            binding.outlineEmail.setError("Vui lòng điền email");
        } else if (!isValidEmail(binding.inputEmail.getText().toString().trim())) {
            flag = false;
            binding.outlineEmail.setError("Sai định dạng email");
        } else if (binding.inputPassword.getText().toString().isEmpty()) {
            flag = false;
            binding.outlinePassword.setError("Vui lòng điền mật khẩu");
            binding.outlinePassword.setPasswordVisibilityToggleEnabled(true);
        } else if (!isValidPassword(binding.inputPassword.getText().toString().trim())) {
            flag = false;
            binding.outlinePassword.setError("Mật khẩu từ 8 đến 20 ký tự, bao gồm chữ cái viết hoa, chữ cái viết thường,số và kí tự đặc biệt");
            binding.outlinePassword.setPasswordVisibilityToggleEnabled(true);
        }
        return flag;

    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 15) {
            return false;
        }
        if (!password.matches(".*\\d+.*")) {
            return false;
        }
        if (!password.matches(".*[a-z]+.*")) {
            return false;
        }
        if (!password.matches(".*[A-Z]+.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()\\-=_+\\[\\]{};':\"\\\\|,.<>/?]+.*")) {
            return false;
        }
        return true;
    }
}
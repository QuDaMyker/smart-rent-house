package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivitySignUpBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivitySignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private ImageButton backToLoginBtn;
    private Button signUpBtn;
    private TextView tv_backToLoginBtn;
    private com.google.android.material.textfield.TextInputEditText signup_fullName, signup_email, signup_phoneNumber, signup_password;
    private com.google.android.material.textfield.TextInputLayout signUpFullname, signUpEmail, signUpPhoneNumber, signUpPassword;
    private String imageURL;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getColor(R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();
        setListeners();
    }

    private void init() {
        preferenceManager = new PreferenceManager(ActivitySignUp.this);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        if (binding.inputPassword.getText().toString().isEmpty()) {
            binding.outlinePassword.setPasswordVisibilityToggleEnabled(false);
        }
    }

    private void setListeners() {
        binding.inputFullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.outlineFullname.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        binding.inputPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.outlinePhoneNumber.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Trước khi text thay đổi

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Trong quá trình text thay đổi
                binding.outlinePassword.setPasswordVisibilityToggleEnabled(true);
                binding.outlinePassword.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Sau khi text đã thay đổi
                if (binding.inputPassword.getText().toString().isEmpty()) {
                    binding.outlinePassword.setPasswordVisibilityToggleEnabled(false);
                } else {
                    binding.outlinePassword.setPasswordVisibilityToggleEnabled(true);
                }
            }
        });
        binding.signupSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkTrueCondition()) {
                    signUpWithEmailPassword();
                }
            }
        });
        binding.backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tvBackToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void signUpWithEmailPassword() {
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information

                    FirebaseUser user = mAuth.getCurrentUser();
                    loadAccountToDataBase();
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(ActivitySignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void saveData() {
        if (checkTrueCondition()) {
            String email = signup_email.getText().toString().trim();
            String password = signup_password.getText().toString().trim();

            createAccount(email, password);
        }
    }

    private void loadAccountToDataBase() {

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String id = currentUser.getUid();
            String name = binding.inputFullname.getText().toString();
            String email = binding.inputEmail.getText().toString();
            String phonenumber = binding.inputPhoneNumber.getText().toString().trim();

            if (currentUser.getPhotoUrl() == null) {
                Uri uriImage = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_default_profile);

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Profiles").child(id);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignUp.this);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                storageReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri urlImage = uriTask.getResult();
                        imageURL = urlImage.toString();

                        dialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                    }
                });
            } else {
                imageURL = currentUser.getPhotoUrl().toString();
            }


            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(now);

            if (imageURL == null) {
                imageURL = "https://cdn.pixabay.com/photo/2023/06/02/14/12/woman-8035772_1280.jpg";
            }
            AccountClass accountCurrent = new AccountClass(name, email, "+84", binding.inputPassword.getText().toString().trim(), imageURL, formattedDate, false, "khong khoa");
            String emailToCheck = email;
            DatabaseReference accountsRef = reference.child("Accounts");
            Query emailQuery = accountsRef.orderByChild("email").equalTo(emailToCheck);

            emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Account with the given email already exists
                        // You can handle this case here
                    } else {
                        DatabaseReference newChildRef = reference.child("Accounts").push();
                        String generatedKey = newChildRef.getKey();

                        // Set the data for the new child node
                        newChildRef.setValue(accountCurrent).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    preferenceManager.putString(Constants.KEY_USER_KEY, generatedKey);
                                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                    preferenceManager.putString(Constants.KEY_USER_KEY, generatedKey);
                                    preferenceManager.putString(Constants.KEY_FULLNAME, accountCurrent.getFullname());
                                    preferenceManager.putString(Constants.KEY_EMAIL, accountCurrent.getEmail());
                                    preferenceManager.putString(Constants.KEY_PASSWORD, accountCurrent.getPassword());
                                    preferenceManager.putString(Constants.KEY_IMAGE, accountCurrent.getImage());
                                    startActivity(new Intent(ActivitySignUp.this, ActivityMain.class));
                                } else {
                                    // Handle the error here
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error here
                }
            });
        }
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loadAccountToDataBase();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(ActivitySignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkTrueCondition() {
        boolean flag = true;
        if (binding.inputFullname.getText().toString().isEmpty()) {
            flag = false;
            binding.outlineFullname.setError("Vui lòng điền họ tên");
        } else if (!isVietnameseString(binding.inputFullname.getText().toString().trim())) {
            flag = false;
            binding.outlineFullname.setError("Vui lòng điền đúng định dạng tên");
        } else if (binding.inputEmail.getText().toString().isEmpty()) {
            flag = false;
            binding.outlineEmail.setError("Vui lòng điền email");
        } else if (!isValidEmail(binding.inputEmail.getText().toString().trim())) {
            flag = false;
            binding.outlineEmail.setError("Sai định dạng email");
        } else if (binding.inputPhoneNumber.getText().toString().isEmpty()) {
            flag = false;
            binding.outlinePhoneNumber.setError("Vui lòng điền số điện thoại");
        } else if (!isVietnamesePhoneNumber(binding.inputPhoneNumber.getText().toString())) {
            flag = false;
            binding.outlinePhoneNumber.setError("Vui lòng điền đúng định dạng số điện thoại");
        } else if (binding.inputPassword.getText().toString().isEmpty()) {
            flag = false;
            binding.outlinePassword.setError("Vui lòng điền mật khẩu");
            binding.outlinePassword.setPasswordVisibilityToggleEnabled(true);
        } else if (!isValidPassword(binding.inputPassword.getText().toString())) {
            flag = false;
            binding.outlinePassword.setError("Mật khẩu từ 8 đến 20 ký tự, bao gồm chữ cái viết hoa, chữ cái viết thường,số và kí tự đặc biệt");
            binding.outlinePassword.setPasswordVisibilityToggleEnabled(true);
        }
        return flag;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        if(password == null) {
            return false;
        }
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

    public static boolean isVietnameseString(String input) {
        if(input == null) {
            return false;
        }
        // Biểu thức chính quy để kiểm tra các ký tự tiếng Việt
        String vietnameseRegex = "^[\\p{L}\\p{M} ]+$";

        // Kiểm tra chuỗi với biểu thức chính quy
        return Pattern.matches(vietnameseRegex, input);
    }

    public static boolean isVietnamesePhoneNumber(String phoneNumber) {
        // Biểu thức chính quy để kiểm tra số điện thoại Việt Nam
        String vietnamesePhoneNumberRegex = "^0[35789]\\d{8}$";

        // Kiểm tra chuỗi số điện thoại với biểu thức chính quy
        return Pattern.matches(vietnamesePhoneNumberRegex, phoneNumber);
    }


}
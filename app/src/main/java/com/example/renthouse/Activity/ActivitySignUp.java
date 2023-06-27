package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
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
    private ImageButton backToLoginBtn;
    private Button signUpBtn;
    private TextView tv_backToLoginBtn;
    private TextInputEditText signup_fullName, signup_email, signup_phoneNumber, signup_password;
    private String imageURL;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Accounts");

        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        signUpBtn = findViewById(R.id.signup_signUpBtn);
        tv_backToLoginBtn = findViewById(R.id.tv_backToLoginBtn);
        signup_fullName = findViewById(R.id.signup_fullName);
        signup_email = findViewById(R.id.signup_email);
        signup_phoneNumber = findViewById(R.id.signup_phoneNumber);
        signup_password = findViewById(R.id.signup_password);

        // Ẩn hiện toggle
        if (signup_password.getText().toString().isEmpty()) {
            TextInputLayout pw = findViewById(R.id.tiploPassword);
            pw.setPasswordVisibilityToggleEnabled(false);
        }

        signup_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Trước khi text thay đổi

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Trong quá trình text thay đổi
                TextInputLayout pw = findViewById(R.id.tiploPassword);
                pw.setPasswordVisibilityToggleEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Sau khi text đã thay đổi
                if (signup_password.getText().toString().isEmpty()) {
                    TextInputLayout pw = findViewById(R.id.tiploPassword);
                    pw.setPasswordVisibilityToggleEnabled(false);
                } else {
                    TextInputLayout pw = findViewById(R.id.tiploPassword);
                    pw.setPasswordVisibilityToggleEnabled(true);
                }
            }
        });

        // nut bam dang ki
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

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
        signup_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                    signUpBtn.performClick();
                    return true;
                }
                return false;
            }
        });


    }
    private void checkTrueCondition() {
        if (signup_fullName.getText().toString().isEmpty()) {
            signup_fullName.setError("Vui lòng điền họ và tên");
            return;
        } else {
            signup_fullName.setError(null);
        }
        if (signup_email.getText().toString().isEmpty()) {
            signup_email.setError("Vui Lòng Điền Email");
            return;
        } else {
            signup_email.setError(null);
        }
        if (signup_phoneNumber.getText().toString().isEmpty()) {
            signup_phoneNumber.setError("Vui lòng điền số điện thoại");
            return;
        } else {
            signup_phoneNumber.setError(null);
        }
        if (signup_password.getText().toString().isEmpty()) {
            signup_password.setError("Vui mật khẩu");
            return;
        } else {
            signup_password.setError(null);
        }
        if (!isValidEmail(signup_email.getText().toString())) {
            signup_email.setError("Vui lòng điền đúng định dạng email");
            return;
        } else {
            signup_email.setError(null);
        }
        if (!isValidPhoneNumber(signup_phoneNumber.getText().toString())) {
            signup_phoneNumber.setError("Vui lòng điền đúng định dạng số điện thoại");
            return;
        } else {
            signup_phoneNumber.setError(null);
        }
        if (!isValidPassword(signup_password.getText().toString())) {
            signup_password.setError("mật khẩu từ 8 đến 20 ký tự, bao gồm chữ cái viết hoa, chữ cái viết thường và số");
            return;
        } else {
            signup_password.setError(null);
        }
    }
    private void saveData() {
        checkTrueCondition();

        String email = signup_email.getText().toString().trim();
        String password = signup_password.getText().toString().trim();

        createAccount(email, password);
    }

    private void loadAccountToDataBase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            String id = currentUser.getUid();
            String name = signup_fullName.getText().toString();
            String email = currentUser.getEmail();
            String phonenumber = signup_phoneNumber.getText().toString().trim();

            if(currentUser.getPhotoUrl() == null) {
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
                });            }
            else {
                imageURL = currentUser.getPhotoUrl().toString();
            }


            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(now);

            if(imageURL == null) {
                imageURL = "https://cdn.pixabay.com/photo/2023/06/02/14/12/woman-8035772_1280.jpg";
            }
            AccountClass accountCurrent = new AccountClass(name, email, "+84", "********", imageURL, formattedDate, false);
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
                        newChildRef.setValue(accountCurrent)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
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
                    Toast.makeText(ActivitySignUp.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPhoneNumber(CharSequence phoneNumber) {
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public boolean isValidPassword(CharSequence password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
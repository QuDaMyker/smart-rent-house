package com.example.renthouse.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.Other.CommonUtils;
import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivityDetailAccount extends AppCompatActivity {
    private TextInputLayout TILfullname, TILemail, TILsodienthoai, TILmatkhau;
    private TextInputEditText TIETfullname, TIETemail, TIETsodienthoai, TIETMatKhau;

    private ImageView imageProfile;
    private ImageButton btnBack;
    private Button btnUpdate;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private boolean isDataLoaded = false;
    private AccountClass account;
    private String key;
    private Uri imageUri;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accounts");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private PreferenceManager preferenceManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_account);

        progressDialog = new ProgressDialog(ActivityDetailAccount.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        preferenceManager = new PreferenceManager(getApplicationContext());

        TILfullname = findViewById(R.id.tiploFullName);
        TILemail = findViewById(R.id.tiploEmail);
        TILsodienthoai = findViewById(R.id.tiploPhoneNumber);
        TILmatkhau = findViewById(R.id.tiploPassword);

        TIETfullname = findViewById(R.id.tiet_fullName);
        TIETemail = findViewById(R.id.tiet_email);
        TIETsodienthoai = findViewById(R.id.tiet_phoneNumber);
        TIETMatKhau = findViewById(R.id.tiet_password);

        progressDialog.dismiss();

        imageProfile = findViewById(R.id.account_personal_imageProfile);
        btnBack = findViewById(R.id.btn_Back);
        btnUpdate = findViewById(R.id.btn_CapNhat);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        Intent intent = getIntent();
        account = (AccountClass) intent.getSerializableExtra("ACCOUNT");
        TIETfullname.setText(preferenceManager.getString(Constants.KEY_FULLNAME));
        TIETemail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        TIETsodienthoai.setText(preferenceManager.getString(Constants.KEY_PHONENUMBER));
        TIETMatKhau.setText(preferenceManager.getString(Constants.KEY_PASSWORD));
        Picasso.get().load(preferenceManager.getString(Constants.KEY_IMAGE)).into(imageProfile);

        key = intent.getStringExtra("KEY");

        btnBack.setOnClickListener(v -> onBackPressed());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTrueCondition()) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference();
                    account.setFullname(TIETfullname.getText().toString().trim());
                    account.setPhoneNumber(TIETsodienthoai.getText().toString().trim());
                    account.setPassword(TIETMatKhau.getText().toString().trim());
                    reference.child("Accounts").child(key).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                    try {
                        currentUser.updatePassword(TIETMatKhau.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //CommonUtils.showNotification(ActivityDetailAccount.this, "Cập nhật mật khẩu", "Cập nhật mật khẩu thành công", R.drawable.ic_phobien_1);
                                    finish();
                                } else {
                                    //CommonUtils.showNotification(ActivityDetailAccount.this, "Cập nhật mật khẩu", "Cập nhật mật khẩu thất bại", R.drawable.ic_phobien_1);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    preferenceManager.putString(Constants.KEY_FULLNAME, TIETfullname.getText().toString().trim());
                    preferenceManager.putString(Constants.KEY_PHONENUMBER, TIETsodienthoai.getText().toString().trim());
                    preferenceManager.putString(Constants.KEY_PASSWORD, TIETMatKhau.getText().toString().trim());
                    preferenceManager.putString(Constants.KEY_USER_KEY, key);
                    onBackPressed();
                }
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    imageUri = data.getData();
                    imageProfile.setImageURI(imageUri);
                    preferenceManager.putString(Constants.KEY_IMAGE, imageUri.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
                if (imageUri != null) {
                    uploadToFireBase(imageUri);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
    }

    public void uploadToFireBase(Uri uri) {
        final StorageReference imageRference = storageReference.child("Image Profiles").child(System.currentTimeMillis() + "." + getFileExtension(uri));

        imageRference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        account.setImage(uri.toString());
                        databaseReference.child(key).setValue(account);
                        progressDialog.dismiss();

                        preferenceManager.putString(Constants.KEY_IMAGE, account.getImage());
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressDialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ActivityDetailAccount.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

    private boolean checkTrueCondition() {
        if (TIETfullname.getText().toString().matches("[0-9\\p{Punct}]+")) {
            TILfullname.setError("Không đúng định dạng");
            return false;
        } else {
            TILfullname.setError(null);
            if (!TIETemail.getText().toString().matches("^[\\w.-]+@[a-zA-Z]+\\.[a-zA-Z]{2,3}$")) {
                TILemail.setError("Không đúng định dạng");
                return false;
            } else {
                TILemail.setError(null);
                if (!TIETsodienthoai.getText().toString().matches("^\\d{10}$")) {
                    TILsodienthoai.setError("Ví dụ: 0912345678");
                    return false;
                } else {
                    TILsodienthoai.setError(null);
                    if (!isValidPassword(TIETMatKhau.getText().toString())) {
                        TILmatkhau.setError("Mật khẩu từ 8 đến 20 ký tự, bao gồm chữ cái viết hoa, chữ cái viết thường và số");
                        return false;
                    } else {
                        TILmatkhau.setError(null);
                        return true;
                    }
                }
            }
        }
    }

    public static boolean isValidPassword(String password) {
        // Kiểm tra độ dài của mật khẩu
        if (password.length() < 8 || password.length() > 15) {
            return false;
        }

        // Kiểm tra có ít nhất một số
        if (!password.matches(".*\\d+.*")) {
            return false;
        }

        // Kiểm tra có ít nhất một chữ thường
        if (!password.matches(".*[a-z]+.*")) {
            return false;
        }

        // Kiểm tra có ít nhất một chữ in hoa
        if (!password.matches(".*[A-Z]+.*")) {
            return false;
        }

        // Kiểm tra có ít nhất một ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*()\\-=_+\\[\\]{};':\"\\\\|,.<>/?]+.*")) {
            return false;
        }

        // Mật khẩu hợp lệ
        return true;
    }
}
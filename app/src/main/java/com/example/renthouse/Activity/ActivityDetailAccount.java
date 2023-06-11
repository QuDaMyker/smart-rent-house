package com.example.renthouse.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    private ProgressBar progressBar;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accounts");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_account);

        TILfullname = findViewById(R.id.tiploFullName);
        TILemail = findViewById(R.id.tiploEmail);
        TILsodienthoai = findViewById(R.id.tiploPhoneNumber);
        TILmatkhau = findViewById(R.id.tiploPassword);

        TIETfullname = findViewById(R.id.tiet_fullName);
        TIETemail = findViewById(R.id.tiet_email);
        TIETsodienthoai = findViewById(R.id.tiet_phoneNumber);
        TIETMatKhau = findViewById(R.id.tiet_password);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        imageProfile = findViewById(R.id.account_personal_imageProfile);
        btnBack = findViewById(R.id.btn_Back);
        btnUpdate = findViewById(R.id.btn_CapNhat);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        Intent intent = getIntent();
        account = (AccountClass) intent.getSerializableExtra("ACCOUNT");
        TIETfullname.setText(account.getFullname());
        TIETemail.setText(account.getEmail());
        TIETsodienthoai.setText(account.getPhoneNumber());
        TIETMatKhau.setText(account.getPassword());
        Picasso.get().load(account.getImage()).into(imageProfile);

        key = intent.getStringExtra("KEY");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Accounts");
                account.setFullname(TIETfullname.getText().toString());
                account.setPhoneNumber(TIETsodienthoai.getText().toString());
                reference.child(key).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

                currentUser.updatePassword(TIETMatKhau.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            CommonUtils.showNotification(ActivityDetailAccount.this, "Cập nhật mật khẩu", "Cập nhật mật khẩu thành công", R.drawable.ic_phobien_1);
                            finish();
                        }else {
                            CommonUtils.showNotification(ActivityDetailAccount.this, "Cập nhật mật khẩu", "Cập nhật mật khẩu thất bại", R.drawable.ic_phobien_1);
                        }
                    }
                });
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    imageUri = data.getData();
                    imageProfile.setImageURI(imageUri);
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
        final StorageReference imageRference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        imageRference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        account.setImage(uri.toString());
                        databaseReference.child(key).setValue(account);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ActivityDetailAccount.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}
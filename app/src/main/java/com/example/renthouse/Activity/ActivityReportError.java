package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.renthouse.Other.CommonUtils;
import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityReportError extends AppCompatActivity {
    private static final int EMAIL_SEND_REQUEST_CODE = 100;
    private TextInputLayout txtInTieuDe, txtInMoTa;
    private TextInputEditText txtEdtTieuDe, txtEdtMoTa;
    private ImageButton btnBack;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_error);

        txtInTieuDe = findViewById(R.id.txtInTieuDe);
        txtInMoTa = findViewById(R.id.txtInMoTaSuCo);

        txtEdtTieuDe = findViewById(R.id.txtEdtTieuDe);
        txtEdtMoTa = findViewById(R.id.txtEdtMoTaSuCo);

        btnBack = findViewById(R.id.btn_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSend = findViewById(R.id.btn_Sent);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                finish();
            }
        });
    }

    private void sendMail() {
        if (txtEdtTieuDe.getText().toString().isEmpty()) {
            txtInTieuDe.setError("Vui Lòng Điền Thông Tin");
        } else if (txtEdtMoTa.getText().toString().isEmpty()) {
            txtInMoTa.setError("Vui Lòng Điền Thông Tin");
        } else {
            String recipientList = "nthienphu163@gmail.com";
            String[] recipients = recipientList.split(",");

            String subject = txtEdtTieuDe.getText().toString();
            String message = txtEdtMoTa.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivityForResult(Intent.createChooser(intent, "Choose an email client"), EMAIL_SEND_REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EMAIL_SEND_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                CommonUtils.showNotification(ActivityReportError.this, "Cảm ơn", "Cảm ơn bạn đã báo cáo lỗi", R.drawable.ic_phobien_1);
                finish();
            } else {
                CommonUtils.showNotification(ActivityReportError.this, "Cảm ơn", "Cảm ơn bạn đã báo cáo lỗi", R.drawable.ic_phobien_1);

            }
        }
    }
}
package com.example.renthouse.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.renthouse.R;
import com.github.barteksc.pdfviewer.PDFView;

public class ActivityAccountPolicy extends AppCompatActivity {

    private PDFView pdfView;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_policy);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("policy.pdf").load();

        btnBack = findViewById(R.id.btn_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
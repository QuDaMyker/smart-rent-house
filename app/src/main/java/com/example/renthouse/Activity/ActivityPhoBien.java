package com.example.renthouse.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.renthouse.OOP.PhoBien;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.utilities.PreferenceManager;

import java.util.List;

public class ActivityPhoBien extends AppCompatActivity {
    PreferenceManager preferenceManager;
    PhoBien phobien;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_phobien);

        //preferenceManager = new PreferenceManager(ActivityPhoBien.this);

       // Intent intent = getIntent();
//        if (intent == null) {
//            return;
//        }
       // phobien = (PhoBien) intent.getSerializableExtra("selectedPhoBien");
    }


}

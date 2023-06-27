package com.example.renthouse.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class Admin_ActivityCreateSchedule extends AppCompatActivity {

    TextInputEditText edtTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_schedule);

        edtTime = findViewById(R.id.edtTime);
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(0)
                        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                        .setTitleText("Chọn thời gian")
                        .setNegativeButtonText("Hủy")
                        .setPositiveButtonText("Chọn");

                MaterialTimePicker timePicker = builder.build();
                timePicker.show(getSupportFragmentManager(), "timePicker");

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();

                        String time = String.format("%02d:%02d", hour, minute);
                        edtTime.setText(time);
                    }
                });
            }
        });
    }
}
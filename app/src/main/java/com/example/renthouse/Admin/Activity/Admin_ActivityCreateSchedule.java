package com.example.renthouse.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.renthouse.Admin.OOP.NotiSchedule;
import com.example.renthouse.BroadcastReceiver.MyAlarmReceiver;
import com.example.renthouse.MyApplication;
import com.example.renthouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Admin_ActivityCreateSchedule extends AppCompatActivity {

    TextInputEditText edtTime;
    TextInputEditText edtTitle;
    TextInputEditText edtContent;
    TextInputEditText edtDate;
    AutoCompleteTextView edtLoop;
    AutoCompleteTextView edtReceiver;

    TextInputLayout edtLayoutTitle;
    TextInputLayout edtLayoutContent;
    TextInputLayout edtLayoutLoop;
    TextInputLayout edtLayoutTime;
    TextInputLayout edtLayoutDate;
    TextInputLayout edtLayoutReceiver;

    MaterialButton createBtn;
    MaterialButton cancelBtn;
    //NotiSchedule editNoti;

    ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_schedule);

        edtTime = findViewById(R.id.edtTime);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtLoop = findViewById(R.id.edtLoop);
        edtReceiver = findViewById(R.id.edtReceiver);
        edtDate = findViewById(R.id.edtDate);

        edtLayoutTitle = findViewById(R.id.edtLayoutTitle);
        edtLayoutContent = findViewById(R.id.edtLayoutContent);
        edtLayoutLoop = findViewById(R.id.edtLayoutLoop);
        edtLayoutTime = findViewById(R.id.edtLayoutTime);
        edtLayoutReceiver = findViewById(R.id.edtLayoutReceiver);
        edtLayoutDate = findViewById(R.id.edtLayoutDate);

        createBtn = findViewById(R.id.createBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        backBtn = findViewById(R.id.btn_Back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        editNoti = null;
//        try {
//            editNoti = (NotiSchedule) getIntent().getExtras().get("notiSchedule");
//            edtTime.setText(editNoti.getTime());
//            edtTitle.setText(editNoti.getTitle());
//            edtContent.setText(editNoti.getContent());
//            edtLoop.setText(editNoti.getLoop(), false);
//            edtReceiver.setText(editNoti.getReceiver(), false);
//            edtDate.setText(editNoti.getDate());
//        }
//        catch (Exception e){
//
//        }

        edtTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutTime.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutTitle.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutContent.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtLoop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutLoop.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtReceiver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutReceiver.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutDate.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Chọn ngày thông báo")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setNegativeButtonText("Hủy")
                        .setPositiveButtonText("Chọn");

                MaterialDatePicker datePicker = builder.build();
                datePicker.show(getSupportFragmentManager(), "DatePicker");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis((Long) selection);
                        int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        int selectedMonth = calendar.get(Calendar.MONTH) + 1;
                        int selectedYear = calendar.get(Calendar.YEAR);

                        String formattedDate = selectedDayOfMonth + "/" + selectedMonth + "/" + selectedYear;
                        edtDate.setText(formattedDate);
                    }
                });
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference notiSchedulesRef = database.getReference("NotiSchedules");
//                    if(editNoti != null){
//                        DatabaseReference editRef = database.getReference("NotiSchedules").child(editNoti.getKey());
//                        NotiSchedule notiSchedule = new NotiSchedule(
//                                editNoti.getKey(),
//                                edtTitle.getText().toString(),
//                                edtContent.getText().toString(),
//                                edtReceiver.getText().toString(),
//                                edtDate.getText().toString(),
//                                edtTime.getText().toString(),
//                                edtLoop.getText().toString());
//                        editRef.setValue(notiSchedule);
//                    }
//                    else{
//                        String key = notiSchedulesRef.push().getKey();
//                        NotiSchedule notiSchedule = new NotiSchedule(
//                                key,
//                                edtTitle.getText().toString(),
//                                edtContent.getText().toString(),
//                                edtReceiver.getText().toString(),
//                                edtDate.getText().toString(),
//                                edtTime.getText().toString(),
//                                edtLoop.getText().toString());
//                        String pathObject = String.valueOf(notiSchedule.getKey());
//                        notiSchedulesRef.child(pathObject).setValue(notiSchedule);
//                    }

                    Date dateTime = null;

                    try {
                        dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(edtDate.getText() + " " + edtTime.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dateTime != null) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmddMM", Locale.getDefault());
                        String requestCodeString = dateFormat.format(dateTime);
                        int requestCode = Integer.parseInt(requestCodeString);
                        // Lấy giờ và phút từ dateTime
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dateTime);

                        // Kiểm tra nếu thời gian thông báo đã qua
                        if (calendar.before(Calendar.getInstance())) {
                            Toast.makeText(Admin_ActivityCreateSchedule.this, "Thời gian đã qua", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            String key = notiSchedulesRef.push().getKey();
                            NotiSchedule notiSchedule = new NotiSchedule(
                                    key,
                                    edtTitle.getText().toString(),
                                    edtContent.getText().toString(),
                                    edtReceiver.getText().toString(),
                                    edtDate.getText().toString(),
                                    edtTime.getText().toString(),
                                    edtLoop.getText().toString());
                            String pathObject = String.valueOf(notiSchedule.getKey());
                            notiSchedulesRef.child(pathObject).setValue(notiSchedule);

                            // Đặt thời gian thông báo cho AlarmManager
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(Admin_ActivityCreateSchedule.this, MyAlarmReceiver.class);
                            intent.putExtra("title", notiSchedule.getTitle());
                            intent.putExtra("content", notiSchedule.getContent());
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, 0);
                            long repeatMode;
                            if(edtLoop.getText().equals("Hàng ngày")){
                                repeatMode = AlarmManager.INTERVAL_DAY;
                            }
                            else {
                                repeatMode = 0;
                            }
                            // Đặt thông số cho AlarmManager
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeatMode, pendingIntent);


                            Toast.makeText(Admin_ActivityCreateSchedule.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent returnIntent = new Intent(Admin_ActivityCreateSchedule.this, Admin_ActivityScheduledNotification.class);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean validateData(){
        boolean flag = true;
        if(TextUtils.isEmpty(edtTitle.getText().toString().trim())){
            edtLayoutTitle.setError("Vui lòng nhập tiêu đề thông báo");
            flag = false;
        }
        if(TextUtils.isEmpty(edtContent.getText().toString().trim())){
            edtLayoutContent.setError("Vui lòng nhập nội dung thông báo");
            flag = false;
        }
        if(TextUtils.isEmpty(edtTime.getText().toString().trim())){
            edtLayoutTime.setError("Vui lòng chọn thời gian");
            flag = false;
        }
        if(TextUtils.isEmpty(edtLoop.getText().toString().trim())){
            edtLayoutLoop.setError("Vui lòng chọn chế độ lặp");
            flag = false;
        }
        if(TextUtils.isEmpty(edtReceiver.getText().toString().trim())){
            edtLayoutReceiver.setError("Vui lòng chọn người nhận");
            flag = false;
        }if(TextUtils.isEmpty(edtDate.getText().toString().trim())){
            edtLayoutDate.setError("Vui lòng chọn ngày");
            flag = false;
        }
        return flag;
    }
}
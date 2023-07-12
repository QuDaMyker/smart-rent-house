package com.example.renthouse;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.renthouse.Admin.Activity.Admin_ActivityCreateSchedule;
import com.example.renthouse.Admin.OOP.NotiSchedule;
import com.example.renthouse.BroadcastReceiver.MyAlarmReceiver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyApplication extends Application {

    public static final String CHANNEL_ID = "push_notification_id";

    @Override
    public void onCreate() {
        super.onCreate();

        createChannelNotification();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notiSchedulesRef = database.getReference("NotiSchedules");
        notiSchedulesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                NotiSchedule notiSchedule = snapshot.getValue(NotiSchedule.class);
                Date dateTime = null;

                try {
                    dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(notiSchedule.getDate() + " " + notiSchedule.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (dateTime != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmddMM", Locale.getDefault());
                    String requestCodeString = dateFormat.format(dateTime);
                    int requestCode = Integer.parseInt(requestCodeString);

                    Intent intent = new Intent(MyApplication.this, MyAlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.this, requestCode, intent, PendingIntent.FLAG_NO_CREATE);

                    if (pendingIntent != null) {
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
                        Log.e("Remove alarm", requestCodeString);
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createChannelNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}

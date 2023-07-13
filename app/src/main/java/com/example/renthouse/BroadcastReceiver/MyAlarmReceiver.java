package com.example.renthouse.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.Admin.OOP.NotiSchedule;
import com.example.renthouse.FCM.Admin_SendNotificationTask;
import com.example.renthouse.FCM.SendNotificationTask;

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Admin_SendNotificationTask task = new Admin_SendNotificationTask(context, title, content);
        task.execute();
    }
}

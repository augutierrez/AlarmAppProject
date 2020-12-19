package com.example.alarmapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    String TAG = "AlarmReceiver";
    String channelID = "Alarms";
    String textTitle = "Alarm App";
    int notificationId = 123;
    String ACTION_SNOOZE = "snooze";
    String ACTION_CANCEL = "cancel";

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "AlarmReceiver called");
        String message = intent.getStringExtra("message");

        try {
            powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmReceiver::Wakelock");
            wakeLock.acquire();


            //context.startForegroundService(new Intent(context, AlarmService.class));
            Intent service = new Intent(context, AlarmService.class);
            service.putExtra("message", message);
            context.startService(service);

            //TODO add a snooze and cancel button to the notification.
            //TODO it doesn't sound when phone is on Do not disturb
        }
        finally {
            wakeLock.release();
        }
    }

}

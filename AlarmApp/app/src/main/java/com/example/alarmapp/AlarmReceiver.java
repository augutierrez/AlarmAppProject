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

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    String channelID = "Alarms";
    String textTitle = "Alarm App";
    int notificationId = 123;
    String ACTION_SNOOZE = "snooze";
    String ACTION_CANCEL = "cancel";

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");

        try {
            powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmReceiver::Wakelock");
            wakeLock.acquire();


            //context.startForegroundService(new Intent(context, AlarmService.class));
            Intent service = new Intent(context, AlarmService.class);
            service.putExtra("message", message);
            context.startService(service);
            /*Intent intent1 = new Intent(context, AlarmOnActivity.class);

            intent1.putExtra("message", message);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(channelID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
                // Configure the notification channel.
                notificationChannel.setDescription("Sample Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.YELLOW);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelID);
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("ALARM!")
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentTitle("Alarm App")
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setContentInfo("Alarm fired");
            notificationManager.notify(1, notificationBuilder.build());


            Notification notification = notificationBuilder.build();
            context.startActivity(intent1); //have this here incase theyre on the app already*/

            //TODO add a snooze and cancel button to the notification.
            //TODO it doesn't sound when phone is on Do not disturb
        }
        finally {
            wakeLock.release();
        }
    }

}

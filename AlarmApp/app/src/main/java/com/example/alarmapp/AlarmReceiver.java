package com.example.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class AlarmReceiver extends BroadcastReceiver {
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmReceiver::Wakelock");
            wakeLock.acquire();
            context.startService(new Intent(context, AlarmService.class));
            Intent intent1 = new Intent(context, AlarmOnActivity.class);
            String message = intent.getStringExtra("message");
            intent1.putExtra("message", message);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
        finally {
            wakeLock.release();
        }
    }
}

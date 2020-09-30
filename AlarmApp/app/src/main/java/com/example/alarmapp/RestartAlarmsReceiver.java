package com.example.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/*
We are going to have to check to see if the alarms were even supposed to be turned on (switch) and if they were valid.
 */
public class RestartAlarmsReceiver extends BroadcastReceiver {
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "RestartAlarmReceiver::Wakelock");
            wakeLock.acquire();
            Log.i("bbaatt", "loaded the alarms");
            AlarmSaver as = new AlarmSaver(context);
            as.loadAlarm();
            ArrayList<Alarm> list = as.list;

            for (Alarm a : list) {
                if (a.isValid()) {
                    a.turnOnAlarm(context.getApplicationContext());
                }
            }
        }
        finally {
            wakeLock.release();
        }
    }
}

package com.example.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import AlarmClasses.Alarm;
import AlarmClasses.AlarmSaver;
//TODO Try adm and check if everything is working ok
/*
We are going to have to check to see if the alarms were even supposed to be turned on (switch) and if they were valid.
 */
public class RestartAlarmsReceiver extends BroadcastReceiver {
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    String TAG = "RestartAlarmsReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "RestartAlarmReceiver::Wakelock");
            wakeLock.acquire();
            Log.i(TAG, TAG + " was called.");

            ArrayList<Alarm> list = null;
            ObjectInput in = null;
            try{
                Log.i(TAG, "alarms loaded");
                //Context directBootContext = context.createDeviceProtectedStorageContext();
                FileInputStream fileInputStream = context.openFileInput("savedAlarms");
                in = new ObjectInputStream(fileInputStream);
                list = (ArrayList<Alarm>) in.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(list == null){
                list = new ArrayList<>();
                Log.i(TAG, "list was null");
            }

            Log.i(TAG, "Turning on alarms!");
            for (Alarm a : list) {
                Log.i(TAG, "checking  for valid alarm");
                if (a.isValid()) {
                    Log.i(TAG, "found valid alarm");
                    a.turnOnAlarm(context.getApplicationContext());

                }
            }
        }
        finally {
            wakeLock.release();
        }
    }
}

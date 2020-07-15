package com.example.alarmapp;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AlarmService extends Service {
    Ringtone r;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        Toast.makeText(getApplicationContext(), "Alarm...", Toast.LENGTH_LONG).show();
        Intent intent1 = new Intent(this, AlarmOnActivity.class);
        String message = intent.getStringExtra("message");
        intent1.putExtra("message", message);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        r.stop();
    }
}

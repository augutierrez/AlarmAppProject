package com.example.alarmapp;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmManager am = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);
        AlarmSaver as = new AlarmSaver(this);
        ArrayList<Alarm> list = as.list;
        Alarm a = list.get(0);
        am.cancel(a.pendingIntent);

        Intent intent = new Intent(DeleteTestActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

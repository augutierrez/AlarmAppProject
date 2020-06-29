package com.example.alarmapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MakeAlarm extends Activity implements View.OnClickListener{
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;
    TimePicker tp; // the time chosen

   private static Button saveButton = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);

        tp = (TimePicker) findViewById(R.id.time_picker);
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
    }



    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MakeAlarm.this, MainActivity.class);
        Toast.makeText(this, "Alarm on!", Toast.LENGTH_SHORT).show();
        //check if its a valid time, otherwise just toast
        startAlarm();
        //startActivity(intent);
    }

    public void startAlarm(){
        //EditText text = findViewById(R.id.time);
        //int x = Integer.parseInt(text.getText().toString());

        int hour = tp.getHour();
        int minute = tp.getMinute();

        //eventually we can get the calendar year,m, and d info too.

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        Log.i("timep", sdf.format(cal.getTime()));
        //int ms = (hour * 60 * 60 * 1000) + (minute * 60 * 1000);

        intent = new Intent(this, AlarmService.class);
        pendingIntent = PendingIntent.getService(this.getApplicationContext(), 234324243, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()/* + (x * 1000)*/, pendingIntent);


        Calendar curr = Calendar.getInstance();


        Toast.makeText(this, "Alarm set in " + (cal.getTimeInMillis() - curr.getTimeInMillis())/1000 + " seconds", Toast.LENGTH_LONG).show();
    }

    public void destroyAlarm(){
        alarmManager.cancel(pendingIntent); // this is needed to cancel the alarm before it even goes off
        stopService(intent); // this is needed to silence the alarm once it does go off
    }


}

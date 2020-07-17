package com.example.alarmapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MakeAlarmActivity extends Activity implements View.OnClickListener{
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;
    TimePicker tp; // the time chosen
    EditText message;

   private static Button saveButton = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);

        message = (EditText) findViewById(R.id.message_edit_text);
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
        Intent intent = new Intent(MakeAlarmActivity.this, MainActivity.class);
        Toast.makeText(this, "Alarm on!", Toast.LENGTH_SHORT).show();
        //check if its a valid time, otherwise just toast
        startAlarm();
        //startActivity(intent);
    }

    public void startAlarm(){
        int hour = tp.getHour();
        int minute = tp.getMinute();

        //eventually we can get the calendar year,m, and d info too.
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, EEE");
        String text = message.getText().toString();

        if(text.isEmpty()){
            text = sdf.format(cal.getTime());
        }

        //saving
        Alarm alarm = new Alarm(cal, pendingIntent, intent, alarmManager, text);
        alarm.turnOnAlarm(getApplicationContext());

        ArrayList<Alarm> list = AlarmSaver.list;
        list.add(alarm);
        //save new alarm into memory
        AlarmSaver alarmSaver = new AlarmSaver(this); //this is prob inefficient to keep creating
        alarmSaver.saveAlarm();

        Intent intent = new Intent(MakeAlarmActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void destroyAlarm(){
        alarmManager.cancel(pendingIntent); // this is needed to cancel the alarm before it even goes off
        stopService(intent); // this is needed to silence the alarm once it does go off
    }
}

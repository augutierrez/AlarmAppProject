package com.example.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Alarm implements Serializable {
    private Calendar cal;
    private String message;
    private boolean switchWidget; // Saves whether the alarm is on
    // these don't get saved because they simply get remade
    public  transient AlarmManager alarmManager;
    public transient PendingIntent pendingIntent;
    private transient Intent intent;

    public Alarm(Calendar cal, String message){
        this.cal = cal;
        this.message = message;
    }

    public Alarm(Calendar cal, PendingIntent pendingIntent, Intent intent, AlarmManager alarmManager) {
        this.cal = cal;
        this.pendingIntent = pendingIntent;
        this.intent = intent;
        this.alarmManager = alarmManager;
        this.message = null;
        this.switchWidget = true;
    }

    public Alarm(Calendar cal, PendingIntent pendingIntent, Intent intent, AlarmManager alarmManager, String message) {
        this.cal = cal;
        this.pendingIntent = pendingIntent;
        if(pendingIntent == null){
            Log.i("Alarm", "Pending intent is null (constructor)");
        }
        else{
            Log.i("Alarm", "pending intent is not null");
        }
        this.intent = intent;
        this.alarmManager = alarmManager;
        this.message = message;
        this.switchWidget = true;
    }


    public Calendar getCalendar(){
        return cal;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        return sdf.format(cal.getTime()).toString();
    }

    public PendingIntent getPendingIntent(){
        return pendingIntent;
    }

    public Intent getIntent(){
        return intent;
    }

    public void switchOff(){
        this.switchWidget = false;
    }

    public void switchOn(){
        this.switchWidget = false;
    }
    public boolean getSwitchWidget(){
        return switchWidget;
    }

    public void turnOffAlarm(){
        if(pendingIntent != null){
            pendingIntent.cancel();
        }
        Log.i("Alarm", "turned off Alarm");
    }

    public void turnOnAlarm(Context context){
        intent = new Intent(context, AlarmReceiver.class);
        Log.i("message", message);
        intent.putExtra("message",  message); // so that we can easily retrieve it for the notification page
        Calendar currTime = Calendar.getInstance();
        long num = currTime.getTimeInMillis();
        pendingIntent = PendingIntent.getBroadcast(context, (int) num, intent, 0);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Calendar curr = Calendar.getInstance();
        Toast.makeText(context, "Alarm set in " + (cal.getTimeInMillis() - curr.getTimeInMillis())/1000 + " seconds", Toast.LENGTH_LONG).show();
    }

    public boolean isValid(){
        if(!switchWidget){
            return false;
        }
        Calendar curr = Calendar.getInstance();
        if(cal.getTimeInMillis() < curr.getTimeInMillis()){
            return false;
        }
        return true;
    }
}

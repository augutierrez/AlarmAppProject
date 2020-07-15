package com.example.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.util.Log;

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

    /*
    In the case that the user turns off the phone, we would restore the intent calendar,
    but the pendingIntent and intent would have have to be restated/ updated.

    Therefore, we would loop through the saved calendars and just give them new p intents and intents.

    I might have to create updateIntent() methods, if we want to let the user "edit"
     */

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
            Log.i("Alarm", "pedngin intent is not nulll");
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

        pendingIntent.cancel();
        Log.i("Alarm", "turned off Alarm");
    }
}

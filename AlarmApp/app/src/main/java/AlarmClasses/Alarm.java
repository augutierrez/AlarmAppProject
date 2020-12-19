package AlarmClasses;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.alarmapp.AlarmReceiver;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//TODO look into enabling the receiver programmatically : https://developer.android.com/training/scheduling/alarms#type

public class Alarm implements Serializable {
    private Calendar cal;
    private String message;
    private final String TAG = "Alarm";
    private boolean switchWidget; // Saves whether the alarm is on
    // these don't get saved because they simply get remade
    public  transient AlarmManager alarmManager;
    public transient PendingIntent pendingIntent;
    private transient Intent intent;

    /**
     * Constructor method
     * @param cal - The Calendar object that contains date and time for the alarm.
     * @param message - The message that will show when the alarm goes off, it's either a custom message or the date and time will show.
     */
    public Alarm(Calendar cal, String message){
        this.cal = cal;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, EEE");
        if(message.isEmpty()){
            Log.i(TAG, "message was empty, putting Calendar date in place");
            message = sdf.format(cal.getTime());
        }
        this.message = message;
        switchWidget = true;
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
        this.switchWidget = true;
    }
    public boolean getSwitchWidget(){
        return switchWidget;
    }

    public void turnOffAlarm(){
        if(pendingIntent != null){
            alarmManager.cancel(pendingIntent);
        }

        Log.i(TAG, "turned off Alarm");
    }

    /**
     * Method responsible for activating the alarm.  Gets called from MakeAlarmActivity as soon as a new alarm is made.
     * @param context - the application context.
     */
    public void turnOnAlarm(Context context){
        //AlarmReceiver is what will handle sounding the alarm when the time comes
        intent = new Intent(context, AlarmReceiver.class);
        Log.i(TAG, message);
        intent.putExtra("message",  message); // so that we can easily retrieve it for the notification page
        Calendar currTime = Calendar.getInstance();
        long num = currTime.getTimeInMillis();
        pendingIntent = PendingIntent.getBroadcast(context, (int) num, intent, 0);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Calendar curr = Calendar.getInstance();
        //TODO make it so that it converts to hours/minutes if the time difference is big
        Toast.makeText(context, "Alarm set in " + (cal.getTimeInMillis() - curr.getTimeInMillis())/1000 + " seconds", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Alarm set in " + (cal.getTimeInMillis() - curr.getTimeInMillis())/1000 + " seconds");
    }

    //TODO check if any method uses this
    public boolean isValid(){
        if(!switchWidget){
            Log.i(TAG, "switch was false");
            return false;
        }
        Calendar curr = Calendar.getInstance();
        if(cal.getTimeInMillis() < curr.getTimeInMillis()){
            Log.i(TAG, "time wasn't valid");
            return false;
        }
        return true;
    }
}

package AlarmClasses;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import AlarmClasses.Alarm;

public class AlarmSaver extends ContextWrapper {
    public static ArrayList<Alarm> list;
    private String TAG = "ALARMSAVER";
    public static boolean createdList;

    public AlarmSaver(Context base) {
        super(base);
        createdList = true;
    }

    public void saveAlarm() {
        ObjectOutput out = null;
        try{
            FileOutputStream fileOutputStream = openFileOutput("savedAlarms",  MODE_PRIVATE);
            out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(list);
            Log.i(TAG, "Alarms written.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAlarm(){
        ObjectInput in = null;
        try{
            Log.i(TAG, "alarms loaded");
            FileInputStream fileInputStream = openFileInput("savedAlarms");
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
        }
    }

    public void deleteAlarmPositions(ArrayList<Integer> positions){
        //keep track of offset since removing an alarm will consequently change the alarm's indexes.
        Collections.sort(positions);
        int offset = 0;
        for (int p : positions){
            int index = p - offset;
            if(index < list.size()){
                Alarm alarm = list.get(index);
                if(alarm.pendingIntent == null){
                    Log.i(TAG, "pending is null");
                }
                else{
                    Log.i(TAG, "pending is not null");
                }
                alarm.turnOffAlarm();
                list.remove(index);
                offset++;
            }
            else{
                Log.i(TAG, "deleteAlarmPositions malfunction");
            }
        }
        saveAlarm();
    }
}
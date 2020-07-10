package com.example.alarmapp;

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

public class AlarmSaver extends ContextWrapper {
    public static ArrayList<Alarm> list;
    private String tag = "ALARMSAVER";

    public AlarmSaver(Context base) {
        super(base);
    }

    public void saveAlarm() {
        ObjectOutput out = null;
        try{
            FileOutputStream fileOutputStream = openFileOutput("savedAlarms",  MODE_PRIVATE);
            out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(list);
            Log.i(tag, "Alarms written.");
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
            Log.i(tag, "alarms loaded");
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
}

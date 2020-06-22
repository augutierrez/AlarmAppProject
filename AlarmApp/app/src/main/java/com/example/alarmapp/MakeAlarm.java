package com.example.alarmapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.Calendar;


public class MakeAlarm extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String[] hours1 = {"0","1"};
    private static final String[] hours2 = {"0","1","2","3","4","5","6","7","8","9"};
    private static final String[] minutes1 = {"0","1","2","3","4","5"};
    private static final String[] minutes2 = {"0","1","2","3","4","5","6","7","8","9"};
    private static final String[] am_pm = {"am", "pm"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);

        TimePicker tp = (TimePicker) findViewById(R.id.time_picker);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

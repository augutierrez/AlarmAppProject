package com.example.alarmapp;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AlarmSaver alarmSaver;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Boolean AlarmsLoaded;
    private String TAG = "MainActivity";
    private ArrayList<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load previous alarms if any

        Log.i(TAG, "FALSE");

        alarmSaver = new AlarmSaver(this);
        //alarmSaver.loadAlarm();

        ArrayList<Alarm> list = alarmSaver.list;
        if(list == null){
            alarmSaver.loadAlarm();
            list = alarmSaver.list;
        }


        Log.i("main", "list size in mian : " + list.size());
        if(list.size()>0){
            if(list.get(0).pendingIntent == null){
                Log.i("main", "pi was null");
            }
            else{
                Log.i("main", "pi was good");
            }
        }


        //in the future I can create an activity separate from main that loads the alarms, that way it only gets loaded once.

        alarms = AlarmSaver.list;
        Log.i(TAG, "number of alarms : " + alarms.size());
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //improves performance since the list stays a single size
        recyclerView.setHasFixedSize(true);
        if(alarms.size()<=0){
            recyclerView.setVisibility(View.GONE);
        }

        TextView textView = (TextView) findViewById(R.id.textView3);
        if(alarms.size()>0){
            textView.setVisibility(View.GONE);
        }



        //using a linear layout
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify adapter
        MainListAdapter adapter = new MainListAdapter(alarms);
        recyclerView.setAdapter(adapter);


        //adds click functionality to the new alarm button
        Button add_alarm_button = (Button) findViewById(R.id.add_alarm);
        add_alarm_button.setOnClickListener(this);

        Button editButton = (Button) findViewById(R.id.edit_main_button);

        if(alarms.size()<=0){
            editButton.setVisibility(View.GONE);
        }
        else{
            editButton.setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_alarm: {
                Toast.makeText(this, "Creating Alarm", Toast.LENGTH_LONG).show();
                Log.i(TAG, "add alarm button was clicked");
                Intent intent = new Intent(MainActivity.this, MakeAlarmActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.edit_main_button: {
                Intent intent = new Intent(MainActivity.this, DeleteAlarmsActivity.class);
                startActivity(intent);
                break;
            }
            default:
                Toast.makeText(getApplicationContext(), "What Happened?", Toast.LENGTH_LONG).show();
                Log.i(TAG, "default case was reached");
        }
    }
}

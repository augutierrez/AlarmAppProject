package com.example.alarmapp;

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

import AlarmClasses.Alarm;
import AlarmClasses.AlarmSaver;

//TODO Fix the UI on the main page.  If an alarm time has passed, it should have the button switched off.
//TODO Alarms don't reload when the app starts again, it doesn't sound
//TODO Separate tests into their own folder
//TODO Check the edit button and the Deleting process, make sure it works fine.
//TODO Write test cases that go through the app

/**
 * The main function, the app starts here.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AlarmSaver alarmSaver;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private String TAG = "MainActivity";
    //List of alarms
    private ArrayList<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //alarmSaver is the class that helps us load previous alarms, or create an empty list if none exist.
        alarmSaver = new AlarmSaver(this);
        alarms = alarmSaver.list;
        if(alarms == null){
            alarmSaver.loadAlarm();
            alarms = alarmSaver.list;
        }

        Log.i(TAG, "alarms list size in main : " + alarms.size());

        if(alarms.size()>0){
            if(alarms.get(0).pendingIntent == null){
                Log.i("main", "pi was null");
            }
            else{
                Log.i("main", "pi was good");
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //improves performance since the list stays a single size
        recyclerView.setHasFixedSize(true);
        //if no alarms, no list will show
        if(alarms.size()<=0){
            recyclerView.setVisibility(View.GONE);
        }
        //This message only gets displayed if the user has 0 alarms
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

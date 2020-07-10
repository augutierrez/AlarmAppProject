package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AlarmSaver alarmSaver;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Boolean AlarmsLoaded = false;
    private String TAG = "MainActivity";
    private ArrayList<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load previous alarms if any
        if(!AlarmsLoaded){
            Log.i("ALARMSLOADED", "FALSE");
            alarmSaver = new AlarmSaver(this);
            alarmSaver.loadAlarm();
            alarms = AlarmSaver.list;
            AlarmsLoaded = true;

        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //improves performance since the list stays a single size
        recyclerView.setHasFixedSize(true);
        MyListAdapter adapter = new MyListAdapter(alarms);

        //using a linear layout
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify adapter
        recyclerView.setAdapter(adapter);


        //adds click functionality to the new alarm button
        Button add_alarm_button = (Button) findViewById(R.id.add_alarm);
        add_alarm_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_alarm: {
                Toast.makeText(this, "Creating Alarm", Toast.LENGTH_LONG).show();
                Log.i(TAG, "add alarm button was clicked");
                Intent intent = new Intent(MainActivity.this, MakeAlarm.class);
                startActivity(intent);
                break;
            }
            default:
                Toast.makeText(getApplicationContext(), "What Happened?", Toast.LENGTH_LONG).show();
                Log.i(TAG, "default case was reached");
        }
    }
}

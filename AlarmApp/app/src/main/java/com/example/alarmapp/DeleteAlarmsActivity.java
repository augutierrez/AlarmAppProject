package com.example.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeleteAlarmsActivity extends AppCompatActivity {
    /*
    If the clicking doesn't work too well, try extending View.OnClickListener, that way any click goes to the same method
    I'm not sure if this will allow you to get the item's position, however.
     */
    private ArrayList<Alarm> alarms;
    private String tag = "deleteAlarmsActivity";
    public static ArrayList<Integer> selectedAlarmPositions;
    private static AlarmSaver alarmSaver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_alarms);

        alarms = AlarmSaver.list;
        selectedAlarmPositions = new ArrayList<>();

        Log.i(tag, "size of alarmList : " + alarms.size());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.delete_recycler_view);
        //improves performance since the list stays a single size
        recyclerView.setHasFixedSize(true);
        DeleteListAdapter adapter = new DeleteListAdapter(alarms);

        //using a linear layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify adapter
        recyclerView.setAdapter(adapter);


        //adds click functionality to the new alarm button
        Button deleteButton = (Button) findViewById(R.id.delete_alarms_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "size = "+selectedAlarmPositions.size());
                for(int i : selectedAlarmPositions){
                    Log.i(tag, ""+i);
                }

                Toast.makeText(getApplicationContext(), "Deleting alarms...", Toast.LENGTH_SHORT).show();
                AlarmSaver alarmSaver = new AlarmSaver(getApplication());
                alarmSaver.deleteAlarmPositions(selectedAlarmPositions);

                Intent intent = new Intent(DeleteAlarmsActivity.this, MainActivity.class);
                startActivity(intent);

                /*
                for some reason the last alarm did not delete.  Make sure that the offset gets reset, also
                don't forget to turn off the alarm service before deleting it off our list, that way the user
                does not get an alarm from a deleted alarm.
                 */
            }
        });
    }
}

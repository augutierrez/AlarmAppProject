package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

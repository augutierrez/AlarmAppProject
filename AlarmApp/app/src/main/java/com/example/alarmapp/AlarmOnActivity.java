package com.example.alarmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class AlarmOnActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_on);

        TextView text = (TextView) findViewById(R.id.alarm_on_text);
        String message = getIntent().getStringExtra("message");
        if(message.isEmpty()){
            message = "Alarm!";
        }
        text.setText(message);

        Button stopAlarmButton = (Button) findViewById(R.id.stop_alarm_button);

        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
                Intent intent = new Intent(AlarmOnActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void stopAlarm(){
        Intent intent = new Intent(this,AlarmService.class);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

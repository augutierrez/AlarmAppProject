package com.example.alarmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class AlarmOnActivity extends Activity {
    String ACTION_SNOOZE = "snooze";
    String ACTION_CANCEL = "cancel";
    EditText delay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_on);

        TextView text = (TextView) findViewById(R.id.alarm_on_text);
        delay = findViewById(R.id.snooze_delay);
        String message = getIntent().getStringExtra("message");
        if(message.isEmpty()){
            message = "Alarm!";
        }
        text.setText(message);

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        //TODO put extras that are necessary
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(AlarmOnActivity.this, AlarmService.class);
                cancel.setAction(ACTION_CANCEL);
                startService(cancel);
                finish();
            }
        });

        Button snoozeButton = (Button) findViewById(R.id.snooze_button);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent snooze = new Intent(AlarmOnActivity.this, AlarmService.class);
                snooze.setAction(ACTION_SNOOZE);
                snooze.putExtra("delay", Integer.parseInt(delay.getText().toString()));
                snooze.putExtra("message", getIntent().getStringExtra("message"));
                startService(snooze);
                finish();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}

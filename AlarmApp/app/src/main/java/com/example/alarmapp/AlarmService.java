package com.example.alarmapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class AlarmService extends Service {
    MediaPlayer mediaPlayer;
    MediaPlayer mp;
    Vibrator vibrator;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    String TAG = "AlarmService";
    Intent intent;
    int notificationID;
    String ACTION_SNOOZE = "snooze";
    String ACTION_CANCEL = "cancel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG, "onStartCommand called");
        this.intent = intent;

        String action = intent.getAction();
        if(action == ACTION_SNOOZE){
            //snooze
        }
        else if(action == ACTION_CANCEL){
            //cancel
        }
        else{
            //start
            startAlarm();
        }
        return START_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG, "The service has been createdd");
    }

    private void startAlarm(){
        Notification notification1 = createNotification();
        startForeground(notificationID, notification1);

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag");
        wakeLock.acquire();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, notification);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final AudioManager audioManager = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);

        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM).build()
        );

        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setLooping(true);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer arg0) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();

            }

        });

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 500, 100, 500, 100, 1000};
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));
        } else {
            vibrator.vibrate(pattern,0);
        }

        Toast.makeText(getApplicationContext(), "Alarm...", Toast.LENGTH_LONG).show();
    }

    private Notification createNotification(){
        Context context = getApplicationContext();
        String channelID = "Alarms";
        String textTitle = "Alarm App";
        notificationID = 123;
        String message = intent.getStringExtra("message");

        Log.i(TAG, "message is : " + message);

        Intent intent1 = new Intent(context, AlarmOnActivity.class);
        intent1.putExtra("message", message);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        ///////////////////////////////////////////////////////////////////////////////////
        //TODO make sure to pass the other extras
        //TODO add an alarm ID as an extra to be able to circle back
        Intent snooze = new Intent(context, AlarmService.class);
        snooze.setAction(ACTION_SNOOZE);
        snooze.putExtra("notificationID", notificationID);
        PendingIntent snoozePendingIntent = PendingIntent.getActivity(context, 0, snooze, 0);

        Intent cancel = new Intent(context, AlarmService.class);
        snooze.setAction(ACTION_CANCEL);
        snooze.putExtra("notificationID", notificationID);
        PendingIntent cancelPendingIntent = PendingIntent.getActivity(context, 0, cancel, 0);



        //TODO take away the activity,make it so only the notification is needed to cancel the alarm.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(channelID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelID);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("ALARM!")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle("Alarm App")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setContentInfo("Alarm fired")
                .addAction(R.drawable.snooze, "snooze", snoozePendingIntent);
        //notificationManager.notify(1, notificationBuilder.build());


        Notification notification = notificationBuilder.build();
        context.startActivity(intent1); //have this here incase theyre on the app already
        return notification;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.reset();
        vibrator.cancel();

        mediaPlayer = MediaPlayer.create(this, R.raw.quack);
        Log.i(TAG, "starting quack");
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.i(TAG, "quack finished");
                mediaPlayer.release();
            }
        });
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        wakeLock.release();
    }
}

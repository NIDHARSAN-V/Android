


package com.example.notificationinterveldismiss;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "break_reminder_channel";
    private static final int NOTIFICATION_ID = 1;
    private final Handler handler = new Handler();
    private final int INTERVAL = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createNotificationChannel();

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        // Start the repeating notification
        startRepeatingNotification();
    }

    private void startRepeatingNotification() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification(MainActivity.this);
                handler.postDelayed(this, INTERVAL); // Repeat every 5 seconds
            }
        }, INTERVAL);
    }



    private void sendNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                // Create an intent for the dismiss action
                Intent dismissIntent = new Intent(context, DismissReceiver.class);
                PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(
                        context, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                // Build the notification with a dismiss button
                Notification notification = new Notification.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Take a Break")
                        .setContentText("You've been working for a while. Take a short break!")
                        .setAutoCancel(true)
                        .addAction(android.R.drawable.ic_delete, "Dismiss", dismissPendingIntent) // Dismiss Button
                        .build();

                notificationManager.notify(NOTIFICATION_ID, notification);
            }
        }
    }


    public static class DismissReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancel(NOTIFICATION_ID);
            }
        }
    }
}



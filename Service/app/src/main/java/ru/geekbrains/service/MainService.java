package ru.geekbrains.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MainService extends IntentService {

    private int messageId = 0;

    public MainService() {
        super("MainService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        makeNote("onHandleIntent");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        makeNote("onCreate");
    }

    @Override
    public void onDestroy() {
        makeNote("onDestroy");
        super.onDestroy();
    }

    private void makeNote(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Main Service notification")
                .setContentText(message);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}

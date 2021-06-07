package com.example.testbroadcastreceiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class TimeChangeRecevier extends BroadcastReceiver {
    static int id;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver called...", Toast.LENGTH_SHORT).show();
        Log.e("Receiver called..","timeChange receiver called....");
        String channeId = "TestID";
        String channelName = "TestChannel";
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(context,channeId);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channeId,channelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            nb.setChannelId(channeId);
        }
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setContentTitle("Alert !");
        nb.setContentText("Device time changed");
        manager.notify(++id,nb.build());

    }
}

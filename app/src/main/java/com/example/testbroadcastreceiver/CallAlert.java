package com.example.testbroadcastreceiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class CallAlert extends BroadcastReceiver {
    static boolean busy = false;
    static boolean ringing = false;
    static boolean idle = false;
    static String number = "";
    static int id;
    @Override
    public void onReceive(Context context, Intent intent) {
       String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
       if(state.equalsIgnoreCase("RINGING")){
           ringing = true;
           number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
       }
       else if(state.equalsIgnoreCase("OFFHOOK")){
           busy = true;
       }
       else if(state.equalsIgnoreCase("IDLE")){
           if(ringing == true && busy == false){
               NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
               String channelId = "MissedCallId";
               String channerName = "MissedCallChannel";
               NotificationCompat.Builder nb = new NotificationCompat.Builder(context,channelId);
               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                   NotificationChannel channel = new NotificationChannel(channelId,channerName,NotificationManager.IMPORTANCE_HIGH);
                   manager.createNotificationChannel(channel);
                   nb.setChannelId(channelId);
               }
               nb.setSmallIcon(R.drawable.ic_baseline_call_24);
               nb.setContentTitle("Missed call");
               nb.setContentText("From : "+number);
               manager.notify(++id,nb.build());
           }
           busy = ringing = idle = false;
           number = "";
       }
    }
}

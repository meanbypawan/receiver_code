package com.example.testbroadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    TestReceiver testReceiver;
    TimeChangeRecevier timeChangeRecevier;
    static int id = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPhoneStatePermission();
        getReadCallLogPermission();
        Button button = findViewById(R.id.button);
        testReceiver = new TestReceiver();
        timeChangeRecevier  = new TimeChangeRecevier();
        Intent in = new Intent("com.example.testbroadcastreceiver.TEST");
        registerReceiver(testReceiver,new IntentFilter("com.example.testbroadcastreceiver.TEST"));
        registerReceiver(timeChangeRecevier,new IntentFilter("android.intent.action.TIME_TICK"));
        registerReceiver(timeChangeRecevier,new IntentFilter("android.intent.action.TIMEZONE_CHANGED"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendBroadcast(in);
            }
        });

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("Battery Low","================================>");
                int batteryPower = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                if(batteryPower == 15 || batteryPower == 10 || batteryPower == 5) {
                    String channeId = "TestID1";
                    String channelName = "TestChannel1";
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder nb = new NotificationCompat.Builder(context, channeId);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(channeId, channelName, NotificationManager.IMPORTANCE_HIGH);
                        manager.createNotificationChannel(channel);
                        nb.setChannelId(channeId);
                    }
                    nb.setSmallIcon(R.mipmap.ic_launcher);
                    nb.setContentTitle("Battery "+batteryPower+"% Low");
                    nb.setContentText("Please plugged in charger");
                    manager.notify(++id, nb.build());
                }

            }
        };
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(testReceiver);
        unregisterReceiver(testReceiver);
    }
    private void getPhoneStatePermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!= PermissionChecker.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},223);
        }
    }
    private void getReadCallLogPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)!= PermissionChecker.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG},224);
        }
    }
}

















package com.example.testbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver called", Toast.LENGTH_SHORT).show();
        Log.e("Receiver called","=========>Receiver called");
    }
}

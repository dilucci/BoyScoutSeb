package com.example.emilovich.boyscout.Entities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "AlarmService.onBind()", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "AlarmService.onStartCommand() ", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "AlarmService.onDestroy()", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "AlarmService.onUnBind()", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }


}

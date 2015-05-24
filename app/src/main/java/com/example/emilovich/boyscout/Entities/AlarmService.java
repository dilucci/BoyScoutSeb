package com.example.emilovich.boyscout.Entities;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Michael on 24-05-2015.
 */

public class AlarmService extends Service {
    private String phoneNumber;
    private String email;

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "AlarmService.onBind()", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "AlarmService.onStartCommand()", Toast.LENGTH_LONG).show();
        phoneNumber = intent.getStringExtra("Phone");
        email = intent.getStringExtra("Email");
        new CountDownTimer(10000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
//                  Intent intent = new Intent(Intent.ACTION_SEND);
//                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  intent.setType("text/plain");
//                  intent.putExtra(Intent.EXTRA_EMAIL, email);
//                  intent.putExtra(Intent.EXTRA_SUBJECT, "WARNING");
//                  intent.putExtra(Intent.EXTRA_TEXT, "A user of BoyScout has not stopped his alarm!");
//                  Intent new_intent = Intent.createChooser(intent, "Send Email");
//                  new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  startActivity(new_intent);

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, "A user of BoyScout has not stopped his alarm!", null, null);
            }
        }.start();
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

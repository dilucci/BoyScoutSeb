package com.example.emilovich.boyscout.Entities;


import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.emilovich.boyscout.Activities.AlarmActivity;

/**
 * Created by Michael on 24-05-2015.
 */

public class AlarmService extends Service {
    private String phoneNumber;
    private String email;
    private long timer;
    private boolean acknowledged;
    private int count = 1;

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "AlarmService.onBind()", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        acknowledged = false;
        Toast.makeText(this, "AlarmService.onStartCommand()" + count, Toast.LENGTH_LONG).show();
        count++;
        phoneNumber = intent.getStringExtra("Phone");
        email = intent.getStringExtra("Email");
        timer = intent.getLongExtra("Timer",-1);

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                AlarmActivity.textViewCounter.setText("seconds: " + (millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
            }
        }.start();


        acknowledgeCount();

        AlertDialog alert = new AlertDialog.Builder(AlarmService.this).create();
        alert.setTitle("Alarm!");
        alert.setMessage("You now have 15 seconds to acknowledge the alarm!!");
        alert.setButton("Acknowledge", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //acknowledged = true;
            }
        });
        alert.show();
        return super.onStartCommand(intent, flags, startId);
    }

    public void acknowledgeCount(){
        //3 minutes pop-up
        new CountDownTimer(15000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if(!acknowledged){
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_EMAIL, email);
//                intent.putExtra(Intent.EXTRA_SUBJECT, "WARNING");
//                intent.putExtra(Intent.EXTRA_TEXT, "A user of BoyScout has not stopped his alarm!");
//                Intent new_intent = Intent.createChooser(intent, "Send Email");
//                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(new_intent);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, "A user of BoyScout has not stopped his alarm!", null, null);
                }
            }
        }.start();
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

package com.example.emilovich.boyscout.Activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emilovich.boyscout.Entities.AlarmService;
import com.example.emilovich.boyscout.R;


public class AlarmActivity extends ActionBarActivity {
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextInterval;
    private TextView textViewStatus;
    private Button buttonAlarm;
    private Button buttonStop;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Intent intent;
    private boolean acknowledged;
    private CountDownTimer cdTimer;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        initUI();
    }

    private void initUI() {
//        GPSActivity.getInstance().setUpMapIfNeeded();
//        currentLocation = GPSActivity.getInstance().getCurrentLocation();
        acknowledged = false;
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        textViewStatus = (TextView)findViewById(R.id.textViewStatus);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextInterval = (EditText)findViewById(R.id.editTextInterval);
        buttonAlarm = (Button)findViewById(R.id.buttonAlarm);
        buttonStop = (Button)findViewById(R.id.buttonStop);
        intent = new Intent(AlarmActivity.this, AlarmService.class);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });
        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!editTextPhone.getText().toString().equals("")) && (!editTextEmail.getText().toString().equals("")) && (!editTextInterval.getText().toString().equals(""))) {
                    startAlarm();
                } else {
                    textViewStatus.setText("Fill out inputs!");
                }
            }
        });
    }

    public void startAlarm(){
        acknowledged = false;
        long alarmTimer = 15000; //10second
//        long alarmTimer = Long.parseLong(editTextInterval.getText().toString())*60000;
//        intent.putExtra("Phone", editTextPhone.getText().toString());
//        intent.putExtra("Email", editTextEmail.getText().toString());
//        intent.putExtra("Timer", alarmTimer);
//        intent.putExtra("Ack", acknowledged);
        pendingIntent = PendingIntent.getService(AlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmTimer, pendingIntent);

        new CountDownTimer(alarmTimer, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                acknowledgeCount();
                alertAlarm();
            }
        }.start();
    }

    public void alertAlarm(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Alarm!");
        alert.setMessage("You now have 30 seconds to acknowledge the alarm!!");
        alert.setButton("Acknowledge", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                acknowledged = true;
                resetAlarm();
                cdTimer.cancel();
            }
        });
        alert.show();
    }

    public void resetAlarm(){
        stopAlarm();
        startAlarm();
    }

    public void stopAlarm(){
        alarmManager.cancel(pendingIntent);
    }

    public void acknowledgeCount(){
        //3 minutes pop-up
        cdTimer = new CountDownTimer(30000, 1000){
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
                    smsManager.sendTextMessage(editTextPhone.getText().toString(), null, "A user of BoyScout has not stopped his alarm! \n coordinates: longitude - " + GPSActivity.currentLocation.getLongitude() + " latitude - " + GPSActivity.currentLocation.getLatitude() , null, null);
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.emilovich.boyscout.Activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.emilovich.boyscout.Entities.AlarmService;
import com.example.emilovich.boyscout.R;

import org.w3c.dom.Text;

import java.util.Calendar;


public class AlarmActivity extends ActionBarActivity {
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextInterval;
    private TextView textViewStatus;
    private Button buttonAlarm;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        initUI();
    }

    private void initUI() {
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        textViewStatus = (TextView)findViewById(R.id.textViewStatus);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextInterval = (EditText)findViewById(R.id.editTextInterval);
        buttonAlarm = (Button)findViewById(R.id.buttonAlarm);
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
        long alarmTimer = 3000; //3seconds
        //long alarmTimer = Long.parseLong(editTextInterval.getText().toString())*60000;
        Intent intent = new Intent(AlarmActivity.this, AlarmService.class);
        intent.putExtra("Phone", editTextPhone.getText().toString());
        intent.putExtra("Email", editTextEmail.getText().toString());
        pendingIntent = PendingIntent.getService(AlarmActivity.this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimer, pendingIntent);

        Toast.makeText(getApplicationContext(), "Alarm started!", Toast.LENGTH_LONG).show();

        new CountDownTimer(alarmTimer, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                AlertDialog alert = new AlertDialog.Builder(AlarmActivity.this).create();
                alert.setTitle("Alarm!");
                alert.setMessage("You now have 3 minutes to acknowledge the alarm!!");
                alert.setButton("Acknowledge", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        resetAlarm();
                    }
                });
                alert.show();
            }
        }.start();
    }

    public void resetAlarm(){
        stopAlarm();
        startAlarm();
    }

    public void stopAlarm(){
        alarmManager.cancel(pendingIntent);
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

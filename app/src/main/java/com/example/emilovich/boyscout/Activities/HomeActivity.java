package com.example.emilovich.boyscout.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.emilovich.boyscout.R;


public class HomeActivity extends ActionBarActivity {
    private Button buttonGPS;
    private Button buttonAlarm;
    private Button buttonMorse;
    private Button buttonCompass;
    private Button buttonWeather;
    private Button buttonCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        initUI();
    }

    private void initUI() {
        buttonGPS = (Button) findViewById(R.id.buttonGPS);
        buttonAlarm = (Button) findViewById(R.id.buttonAlarm);
        buttonMorse = (Button) findViewById(R.id.buttonMorse);
        buttonCompass = (Button) findViewById(R.id.buttonCompass);
        buttonWeather = (Button) findViewById(R.id.buttonWeather);
        buttonCamera = (Button) findViewById(R.id.buttonCamera);

        buttonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GPSActivity.class);
                startActivity(i);
            }
        });
        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(i);
            }
        });
        buttonMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MorseActivity.class);
                startActivity(i);
            }
        });
        buttonCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CompassActivity.class);
                startActivity(i);
            }
        });
        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
                startActivity(i);
            }
        });
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

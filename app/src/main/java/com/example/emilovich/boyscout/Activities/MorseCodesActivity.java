package com.example.emilovich.boyscout.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.emilovich.boyscout.R;


public class MorseCodesActivity extends ActionBarActivity {
    private CheckBox checkBoxVibration;
    private SharedPreferences settings;
    private boolean chooseVibe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_codes);

        chooseVibe = true;
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        checkBoxVibration = (CheckBox)findViewById(R.id.checkBoxVibration);
        checkBoxVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chooseVibe = checkBoxVibration.isChecked();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_morse_codes, menu);
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

    @Override
    protected void onPause() {
        //persist in sharedPreferences - primitive data types.
        super.onPause();
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("chooseVibrator", chooseVibe);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkBoxVibration.setChecked(settings.getBoolean("chooseVibrator", true));
    }
}

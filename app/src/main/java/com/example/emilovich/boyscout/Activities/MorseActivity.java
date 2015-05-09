package com.example.emilovich.boyscout.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.emilovich.boyscout.Entities.MorseCode;
import com.example.emilovich.boyscout.R;


public class MorseActivity extends ActionBarActivity {
    private Button buttonMorseSOS;
    private Button buttonMorseHelp;
    private Button buttonMorse;
    private Button buttonMorseCodes;
    private Context context;

    private boolean hasFlash;
    private boolean isFlashlightOn;
    private Camera camera;
    private Parameters params;

    private MorseCode morseCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

        initUI();
    }

    private void initUI() {
        morseCodes = new MorseCode();
        context = getApplicationContext();
        buttonMorseSOS = (Button) findViewById(R.id.buttonMorseSOS);
        buttonMorseHelp = (Button) findViewById(R.id.buttonMorseHelp);
        buttonMorse = (Button) findViewById(R.id.buttonMorse);
        buttonMorseCodes = (Button) findViewById(R.id.buttonMorseCodes);
        setUpCamera();

        buttonMorseCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MorseCodesActivity.class);
                startActivity(i);
            }
        });

        buttonMorseSOS.setOnClickListener(new View.OnClickListener() {
            String[] sequence;
            @Override
            public void onClick(View v) {
                sequence = morseCodes.getMorseSequence("SOS");
            }
        });

        buttonMorseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasFlash){
                    if (isFlashlightOn){
                        flashlightOff();
                    }else{
                        flashlightOn();
                    }
                }else{
                    AlertDialog alert = new AlertDialog.Builder(MorseActivity.this).create();
                    alert.setTitle("No flashlight!");
                    alert.setMessage("Your device does not support this feature!");
                    alert.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.show();
                }
            }
        });
    }
    //check if phone has flashlight and sets up camera
    private void setUpCamera(){
        hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        camera = Camera.open();
        params = camera.getParameters();
    }

    private void flashlightOn(){
        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashlightOn = true;
    }

    private void flashlightOff(){
        params.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashlightOn = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_morse, menu);
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
        super.onPause();
        flashlightOff();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}

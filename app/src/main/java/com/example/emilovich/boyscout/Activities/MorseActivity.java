package com.example.emilovich.boyscout.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emilovich.boyscout.Entities.MorseCode;
import com.example.emilovich.boyscout.Entities.MorseController;
import com.example.emilovich.boyscout.Entities.MorseHandler;
import com.example.emilovich.boyscout.R;

import java.util.ArrayList;


public class MorseActivity extends ActionBarActivity {
    private MorseController morseController;

    private Button buttonMorseSOS;
    private Button buttonMorseHelp;
    private Button buttonMorse;
    private Button buttonMorseCodes;
    private EditText morseText;
    private Context context;
    private ArrayList<String> sequence;

    private Vibrator vibe;
    private boolean hasFlash;
    private MorseCode morseCodes;

    public MorseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

        initUI();
    }

    private void initUI() {
        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        morseController = new MorseController(vibe);
        morseCodes = new MorseCode();
        sequence = new ArrayList<>();
        context = getApplicationContext();
        hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        morseText = (EditText) findViewById(R.id.morseText);
        buttonMorseSOS = (Button) findViewById(R.id.buttonMorseSOS);
        buttonMorseHelp = (Button) findViewById(R.id.buttonMorseHelp);
        buttonMorse = (Button) findViewById(R.id.buttonMorse);
        buttonMorseCodes = (Button) findViewById(R.id.buttonMorseCodes);
        //setUpCamera();

        buttonMorseCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MorseCodesActivity.class);
                startActivity(i);
            }
        });

        //Morse SOS
        buttonMorseSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasFlash){
                    //morseController.stopBlink();
                    sequence = morseCodes.getMorseSequence("SOS");
                    Toast.makeText(getApplicationContext(), "sequence: " + sequence.toString(),
                            Toast.LENGTH_LONG).show();
                    MorseHandler morseHandler = new MorseHandler(morseController, sequence);
                    Thread thread = new Thread(morseHandler);
                    thread.start();
                    transmitAlert("SOS", morseHandler);
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

        //Morse Help
        buttonMorseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasFlash){
                    //morseController.stopBlink();
                    sequence = morseCodes.getMorseSequence("HELP");
                    Toast.makeText(getApplicationContext(), "sequence: " + sequence.toString(),
                            Toast.LENGTH_LONG).show();
                    MorseHandler morseHandler = new MorseHandler(morseController, sequence);
                    Thread thread = new Thread(morseHandler);
                    thread.start();
                    transmitAlert("HELP", morseHandler);
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

        //Morse customer-text
        buttonMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasFlash){
                    //morseController.stopBlink();
                    sequence = morseCodes.getMorseSequence(morseText.getText().toString());
                    MorseHandler morseHandler = new MorseHandler(morseController, sequence);
                    Thread thread = new Thread(morseHandler);
                    thread.start();
                    transmitAlert(morseText.getText().toString(), morseHandler);
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

    private void transmitAlert(String message, final MorseHandler morseHandler){
        AlertDialog.Builder builder = new AlertDialog.Builder(MorseActivity.this);
        builder.setTitle("Transmitting...");
        builder.setMessage("Transmitting morse message: " + message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("STOP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                morseController.stopBlink();
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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

    //check if phone has flashlight and sets up camera
    /*public static void setUpCamera() {
        if (camera == null || params == null) {
            camera = Camera.open();
            params = camera.getParameters();
            MorseHandler.stopblink = false;
        }
    }

    public static void releaseCamera(){
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public static void flashlightOn() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    public static void flashlightOff() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
    }*/

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        //camera.open
        super.onResume();
        morseController.setUpCamera();
}
}

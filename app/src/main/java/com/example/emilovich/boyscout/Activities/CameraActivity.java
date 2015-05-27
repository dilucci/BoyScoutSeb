package com.example.emilovich.boyscout.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Camera;
import android.view.View;
import android.widget.Button;

import com.example.emilovich.boyscout.R;

public class CameraActivity extends ActionBarActivity {
    private boolean hasCamera;
    private Context context;
    private Camera camera;
    private Camera.Parameters params;
    private static final int IMAGE_CAPTURE = 102;
    private Button buttonCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initUI();
    }

    private void initUI() {
        context = getApplicationContext();
        hasCamera = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        buttonCamera = (Button) findViewById(R.id.buttonCamera);
        if (hasCamera) {
            buttonCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAPTURE);
                }
            });
        } else {
            AlertDialog alert = new AlertDialog.Builder(CameraActivity.this).create();
            alert.setTitle("No camera!");
            alert.setMessage("Your device does not support this feature!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
    }

    public void setUpCamera() {
        if (camera == null || params == null) {
            camera = Camera.open();
            params = camera.getParameters();
        }
    }

    public void releaseCamera(){
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }
}

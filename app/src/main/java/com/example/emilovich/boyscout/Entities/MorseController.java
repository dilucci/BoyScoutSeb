package com.example.emilovich.boyscout.Entities;

import android.hardware.Camera;
import android.os.Vibrator;

/**
 * Created by Michael on 18-05-2015.
 */

public class MorseController {
    private Camera camera;
    private Camera.Parameters params;
    private boolean stopBlink;
    private Vibrator vibe;
    public MorseController(Vibrator vibe){
        this.vibe = vibe;
    }
    public void setUpCamera() {
        if (camera == null || params == null) {
            camera = Camera.open();
            params = camera.getParameters();
        }
        stopBlink = false;
    }

    public void releaseCamera(){
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
            stopBlink = true;
        }
    }

    public void flashlightOn() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    public void flashlightOff() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
    }

    public void vibrate(int duration){
        vibe.vibrate(duration);
    }

    public boolean getStopBlink(){
        return stopBlink;
    }

    public void stopBlink() {
        stopBlink = true;
    }
}

package com.example.emilovich.boyscout.Entities;

import android.hardware.Camera;
import android.os.Vibrator;

public class MorseController {
    private Camera camera;
    private Camera.Parameters params;
    private boolean stopBlink;
    private boolean flashOn;
    private boolean chooseVibe;
    private Vibrator vibe;

    public MorseController(Vibrator vibe){
        flashOn = false;
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
        flashOn = true;
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    public void flashlightOff() {
        flashOn = false;
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

    public void toggleFlashlight(){
        stopBlink();
        if(flashOn){
            flashlightOff();
        }
        else{
            flashlightOn();
        }
    }

    public boolean isChooseVibe() {
        return chooseVibe;
    }

    public void setChooseVibe(boolean chooseVibe) {
        this.chooseVibe = chooseVibe;
    }
}

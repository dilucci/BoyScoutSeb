package com.example.emilovich.boyscout.Entities;

import android.hardware.Camera;
import android.util.Log;
import android.widget.Toast;

import com.example.emilovich.boyscout.Activities.MorseActivity;

import java.util.ArrayList;

/**
 * Created by Michael on 12-05-2015.
 */
public class MorseHandler implements Runnable {
    //private String TAG = "Handler";
    private MorseController morseController;

    private ArrayList<String> sequence = new ArrayList<>();

    public MorseHandler(MorseController morseController, ArrayList<String> morseSequence) {
        this.morseController = morseController;
        morseController.setUpCamera();
        this.sequence = morseSequence;
    }

    public void stopThread() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        //Log.d(TAG, "inde i run()!");
        String letterSeq = "";
        char letter;
        for (int i = 0; i < sequence.size(); i++) { //ORD
            letterSeq = sequence.get(i);
            for (int k = 0; k < letterSeq.length(); k++) { // bogstav
                //Log.d(TAG, "inde i for-løkke!");
                if (morseController.getStopBlink()) {
                    //Log.d(TAG, "BREAK!");
                    stopThread();
                    break;
                }else{
                    letter = letterSeq.charAt(k);
                    if (letter == '.') {
                        try {
                            morseController.flashlightOn();
                            morseController.vibrate(250);
                            //Log.d(TAG, "FlashlightOn()!");
                            Thread.sleep(250);
                            morseController.flashlightOff();
                            //Log.d(TAG, "FlashlightOff()!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (letter == '-') {
                        try {
                            morseController.flashlightOn();
                            morseController.vibrate(1000);
                            Thread.sleep(1000);
                            morseController.flashlightOff();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (letter == '|') {
                        try {
                            Thread.sleep(2000); // Mellemrum!
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(400); // Efter hvert blink!
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000); // Efter hvert bogstav!
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        stopThread();
    }
}

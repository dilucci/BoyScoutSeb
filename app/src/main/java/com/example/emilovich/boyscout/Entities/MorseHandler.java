package com.example.emilovich.boyscout.Entities;

import android.hardware.Camera;

import com.example.emilovich.boyscout.Activities.MorseActivity;

import java.util.ArrayList;

/**
 * Created by Michael on 12-05-2015.
 */
public class MorseHandler implements Runnable {

    public static boolean stopblink;
    private ArrayList<String> sequence = new ArrayList<>();

    public MorseHandler(ArrayList<String> morseSequence) {

        MorseActivity.setUpCamera();
        this.sequence = morseSequence;
    }

    public static void stopThread() {
        /*if (Thread.currentThread() != null) {*/
            Thread.currentThread().interrupt();

        MorseActivity.releaseCamera();
    }

    public static void lastblink() {
        stopblink = true;
    }

    @Override
    public void run() {
        String letterSeq = "";
        char letter;

        for (int i = 0; i < sequence.size(); i++) { //ORD
            letterSeq = sequence.get(i);
            for (int k = 0; k < letterSeq.length(); k++) { // bogstav
                if (!stopblink) { //skulle køre sidste blink
                    letter = letterSeq.charAt(k);
                    if (letter == '.') {
                        try {
                            MorseActivity.flashlightOn();
                            Thread.sleep(250);
                            MorseActivity.flashlightOff();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (letter == '-') {
                        try {
                            MorseActivity.flashlightOn();
                            Thread.sleep(1000);
                            MorseActivity.flashlightOff();
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
                } else {
                    stopThread();
                }
                try {
                    Thread.sleep(1000); // Efter hvert bogstav!
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }stopThread();
    }
}

package com.example.emilovich.boyscout.Entities;

import java.util.ArrayList;

public class MorseHandler implements Runnable {
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
        String letterSeq = "";
        char letter;
        for (int i = 0; i < sequence.size(); i++) { //ORD
            letterSeq = sequence.get(i);
            for (int k = 0; k < letterSeq.length(); k++) { // bogstav
                if (morseController.getStopBlink()) {
                    stopThread();
                    break;
                }else{
                    letter = letterSeq.charAt(k);
                    if (letter == '.') {
                        try {
                            morseController.flashlightOn();
                            if(morseController.isChooseVibe())
                                morseController.vibrate(250);
                            Thread.sleep(250);
                            morseController.flashlightOff();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (letter == '-') {
                        try {
                            morseController.flashlightOn();
                            if(morseController.isChooseVibe())
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

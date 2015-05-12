package com.example.emilovich.boyscout.Entities;

import java.util.ArrayList;

/**
 * Created by Michael on 12-05-2015.
 */
public class MorseHandler implements Runnable {
    private ArrayList<String> sequence = new ArrayList<>();
    public MorseHandler(ArrayList<String> morseSequence){
        this.sequence = morseSequence;
    }
    @Override
    public void run() {
        String letterSeq = "";
        char letter;
        for (int i = 0; i < sequence.size(); i++){
            letterSeq = sequence.get(i);
            for (int k = 0; k < letterSeq.length(); k++){
                letter = letterSeq.charAt(k);
                if(letter == '.'){
                    try {
                        flashlightOn();
                        thread.sleep(250);
                        flashlightOff();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(letter == '-'){
                    try {
                        flashlightOn();
                        thread.sleep(1000);
                        flashlightOff();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(letter == '|'){
                    try {
                        thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

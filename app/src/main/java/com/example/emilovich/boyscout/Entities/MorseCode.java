package com.example.emilovich.boyscout.Entities;

import java.util.ArrayList;

/**
 * Created by Seb on 09-05-2015.
 */
public class MorseCode {
    private char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' '};
    private String[] morseSequence = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--..","|"};

    public ArrayList<String> getMorseSequence(String stringToMorse) {
        stringToMorse = stringToMorse.toLowerCase();
        //String str = "sos";
        //String[] sosSequence = {"...","---","..."};
        ArrayList<String> sequence = new ArrayList<>();

        for (int i = 0; i < stringToMorse.length();i++){
            char letter = stringToMorse.charAt(i);
            for (int k = 0; k < alphabet.length; k++){
                if (letter == alphabet[k]){
                    sequence.add(morseSequence[k]);
                }
            }
        }
        return sequence;
    }
}

package edu.sdccd.cisc191;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CipherTools {
    public static final double[] LETTER_FREQ = {0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.000153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.0236, 0.0015, 0.01974, 0.00074};

    /**************************************************************************
     * Returns array of how many times each letter has appeared
     *************************************************************************/
    public static int[] getLetterFrequency(String inputText) {
        int[] letterFrequency = new int[26];
        for(int i=0; i<inputText.length(); i++) {
            letterFrequency[inputText.charAt(i) - 'A']++;
        }
        return letterFrequency;
    }

    /**************************************************************************
     * Friedman test to find index of coincidence
     *************************************************************************/
    public static double findIndexOfCoincidence(int length, int[] letterFrequency) {
        double IoC = 0.0;
        int denom = length*(length-1);    //Finds index of coincidence
        for(int i=0; i<26; i++)
            IoC += (double) (letterFrequency[i] * (letterFrequency[i] - 1)) / denom;
        return IoC;
    }

    /**************************************************************************
     * Compare how closely a text resembles plain english
     *************************************************************************/
    public static double chiSquareTest(int length, int[] letterFrequency) {
        double chiSquared = 0.0;
        for(int l=0; l<26; l++)
            chiSquared += (Math.pow((letterFrequency[l] - (length*LETTER_FREQ[l])),2))/(LETTER_FREQ[l]*length);
        return chiSquared;
    }

    /**************************************************************************
     * Compares how closely a shifted text resembles plain english
     *************************************************************************/
    public static double chiSquareTestShifted(int length, int[] letterFrequency, int shift) {
        double chiSquared = 0.0;
        for(int l=0; l<26; l++)
            chiSquared += (Math.pow((letterFrequency[(l+shift)%26] - (length*LETTER_FREQ[l])),2))/(LETTER_FREQ[l]*length);
        return chiSquared;
    }
    public static int romantoInteger(String romanNumeral){
        int integer = 0;
        switch(romanNumeral){
            case("I"):
                integer = 1;
                break;
            case("II"):
                integer = 2;
                break;
            case("III"):
                integer = 3;
                break;
            case("IV"):
                integer = 4;
                break;
            case("V"):
                integer = 5;
                break;
            case("VI"):
                integer = 6;
                break;
            case("VII"):
                integer = 7;
                break;
            case("VIII"):
                integer = 8;
                break;
        }
        return integer;
    }

    public static String getUrl(String input) throws Exception {
        try {
            Document document = Jsoup.connect(input).get();
            return document.text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

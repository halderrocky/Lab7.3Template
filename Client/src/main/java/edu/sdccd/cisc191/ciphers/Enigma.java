package edu.sdccd.cisc191.ciphers;

import edu.sdccd.cisc191.CipherTools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Enigma extends CipherTools{
    private final int[] UKWB = {24,17,20,7,16,18,11,3,15,23,13,6,14,10,12,8,4,1,5,25,2,22,21,9,0,19};
    private final int[] UKWC = {5,21,15,9,8,0,14,24,4,3,17,25,23,22,6,2,19,10,20,16,18,1,13,12,7,11};

    private Rotor rotor1;
    private Rotor rotor2;
    private Rotor rotor3;
    private int[] reflector;
    private HashMap<Character, Character> plugboard = new HashMap<>();

    public Enigma (int[] rotor1, int[] rotor2, int[] rotor3, char reflectorType, String letterPairs) {
        this.rotor1 = new Rotor(rotor1[0], rotor1[1], rotor1[2]);
        this.rotor2 = new Rotor(rotor2[0], rotor2[1], rotor2[2]);
        this.rotor3 = new Rotor(rotor3[0], rotor3[1], rotor3[2]);
        if(reflectorType == 'B')
            reflector = UKWB;
        else
            reflector = UKWC;
        createPlugboard(letterPairs.toUpperCase());
    }

    public String encode (String inputText) {
        int[] rotorPositions = {rotor1.getRotorPosition(), rotor2.getRotorPosition(), rotor3.getRotorPosition()};

        StringBuilder output = new StringBuilder();
        for(char c : inputText.toUpperCase().toCharArray()) {
            if (rotor3.getRotorPosition() == rotor3.getTurnoverPoint())
                rotor2.shift();
            if (rotor2.getRotorPosition() == rotor2.getTurnoverPoint() && rotor3.getRotorPosition() == (rotor3.getTurnoverPoint()+1)%26) {
                rotor1.shift();
                rotor2.shift();
            }
            rotor3.shift();
            output.append(plugboard.get(enigmaTransform(plugboard.get(c))));
        }

        rotor1.setRotorPosition(rotorPositions[0]);
        rotor2.setRotorPosition(rotorPositions[1]);
        rotor3.setRotorPosition(rotorPositions[2]);

        return output.toString();
    }

    public static String cryptanalyze(String inputText) {
        int[] probableSettings = new int[9];
        double maxIoC = Double.MIN_VALUE;
        char reflectorType = 'B';

        /*Enigma testReflectorB = new Enigma(new int[]{1,1,1}, new int[]{1,1,1}, new int[]{1,1,1}, 'B', "");
        Enigma testReflectorC = new Enigma(new int[]{1,1,1}, new int[]{1,1,1}, new int[]{1,1,1}, 'C', "");
        if(findIndexOfCoincidence(inputText.length(),getLetterFrequency(testReflectorB.encode(inputText))) < findIndexOfCoincidence(inputText.length(),getLetterFrequency(testReflectorC.encode(inputText))))
            reflectorType = 'C';*/

        System.out.println("Most Probable Reflector: UKW " + reflectorType + "\n");

        for (int iteration = 0; iteration < 125; iteration++) {
            double rotorIoC = 0;
            int[] currentOrder = {1+iteration/25, 1+(iteration/5)%5, 1+iteration%5};
            if(currentOrder[0] != currentOrder[1] && currentOrder[0] != currentOrder[2] && currentOrder[1] != currentOrder[2]) {
                for (int i = 1; i < 27; i++) {
                    for (int j = 1; j < 27; j++) {
                        for (int k = 1; k < 27; k++) {
                            Enigma enigma = new Enigma(new int[]{currentOrder[0], i, 1}, new int[]{currentOrder[1], j, 1}, new int[]{currentOrder[2], k, 1}, reflectorType, "");
                            double IoC = findIndexOfCoincidence(inputText.length(), getLetterFrequency(enigma.encode(inputText)));
                            if (IoC > maxIoC) {
                                maxIoC = IoC;
                                probableSettings = new int[]{currentOrder[0], currentOrder[1], currentOrder[2], i, j, k, 1, 1, 1};
                            }
                            rotorIoC = Math.max(IoC, rotorIoC);
                        }
                    }
                }
                System.out.println(Arrays.toString(currentOrder) + "\t" + rotorIoC);
            }
        }

        System.out.println("\nMost Probable Rotor Order: \n" + Arrays.toString(Arrays.copyOfRange(probableSettings,0,3)) + "\n");

        //Rotor 3 -- Fastest Rotor
        int rotorPos = probableSettings[5];
        for(int i=1; i<27; i++) {
            Enigma enigma = new Enigma(new int[]{probableSettings[0], probableSettings[3], 1}, new int[]{probableSettings[1], probableSettings[4], 1}, new int[]{probableSettings[2], rotorPos, i}, reflectorType, "");
            double IoC = findIndexOfCoincidence(inputText.length(), getLetterFrequency(enigma.encode(inputText)));
            if (IoC > maxIoC) {
                maxIoC = IoC;
                probableSettings[8] = i;
            }
            rotorPos = (probableSettings[5]+i)/27 + (probableSettings[5]+i)%27;
        }
        probableSettings[5] = (probableSettings[5]+probableSettings[8])/26 + (probableSettings[5]+probableSettings[8])%26 - 1;

        //Rotor 2 -- Middle Rotor
        rotorPos = probableSettings[4];
        for(int i=1; i<27; i++) {
            Enigma enigma = new Enigma(new int[]{probableSettings[0], probableSettings[3], 1}, new int[]{probableSettings[1], rotorPos, i}, new int[]{probableSettings[2], probableSettings[5], probableSettings[8]}, reflectorType, "");
            double IoC = findIndexOfCoincidence(inputText.length(), getLetterFrequency(enigma.encode(inputText)));
            if (IoC > maxIoC) {
                maxIoC = IoC;
                probableSettings[7] = i;
            }
            rotorPos = (probableSettings[4]+i)/27 + (probableSettings[4]+i)%27;
        }
        probableSettings[4] = (probableSettings[4]+probableSettings[7])/26 + (probableSettings[4]+probableSettings[7])%26 - 1;

        System.out.println("Most Probable Rotor Settings [Rotor Order] [Initial Position] [Ring Setting]:\n" + Arrays.toString(probableSettings) + "\t" + maxIoC);

        HashSet<Integer> pluggedLetters = new HashSet<>();
        String currentBoard = "";
        for(int iteration=0; iteration<10; iteration++){
            String bestPair = "";

            for(int i=0; i<26; i++) {
                for(int j=0; j<26; j++) {
                    if(!pluggedLetters.contains(i) && !pluggedLetters.contains(j)) {
                        String plugboardPair = String.valueOf((char)(i + 'A')) + (char) (j + 'A');
                        Enigma enigma = new Enigma(new int[]{probableSettings[0], probableSettings[3], probableSettings[6]}, new int[]{probableSettings[1], probableSettings[4], probableSettings[7]}, new int[]{probableSettings[2], probableSettings[5], probableSettings[8]}, reflectorType, currentBoard + plugboardPair);
                        double IoC = findIndexOfCoincidence(inputText.length(), getLetterFrequency(enigma.encode(inputText)));
                        if (IoC > maxIoC) {
                            maxIoC = IoC;
                            bestPair = plugboardPair;
                        }
                    }
                }
            }

            if(bestPair.length() < 2)
                break;
            currentBoard += bestPair + " ";
            pluggedLetters.add(bestPair.charAt(0)-'A');
            pluggedLetters.add(bestPair.charAt(1)-'A');
        }

        System.out.println("\nMost Probable Plugboard Configuration:\n" + currentBoard + "\t" + maxIoC);


        Enigma enigma = new Enigma(new int[]{probableSettings[0], probableSettings[3], probableSettings[6]}, new int[]{probableSettings[1], probableSettings[4], probableSettings[7]}, new int[]{probableSettings[2], probableSettings[5], probableSettings[8]}, 'B', currentBoard);

        System.out.println("\nDecrypted Message:\n" + enigma.encode(inputText));

        return enigma.encode(inputText);
    }

    public char enigmaTransform (char c) {
        int l = c-'A';

        l = rotor3.transform(l);
        l = rotor2.transform(l);
        l = rotor1.transform(l);
        l = reflector[l];
        l = rotor1.reverseTransform(l);
        l = rotor2.reverseTransform(l);
        l = rotor3.reverseTransform(l);

        return (char) (l + 'A');
    }

    private void createPlugboard (String letterPairs) {
        String[] pairs = letterPairs.split(" ");
        for(String str : pairs) {
            if(str.length() == 2) {
                plugboard.put(str.charAt(0), str.charAt(1));
                plugboard.put(str.charAt(1), str.charAt(0));
            }
        }
        for(char c='A'; c<='Z'; c++) {
            if(!plugboard.containsKey(c))
                plugboard.put(c,c);
        }
    }

    private class Rotor {
        private final int[] ROTOR_1 = {4,10,12,5,11,6,3,16,21,25,13,19,14,22,24,7,23,20,18,15,0,8,1,17,2,9};
        private final int[] ROTOR_2 = {0,9,3,10,18,8,17,20,23,1,11,7,22,19,12,2,16,6,25,13,15,24,5,21,14,4};
        private final int[] ROTOR_3 = {1,3,5,7,9,11,2,15,17,19,23,21,25,13,24,4,8,22,6,0,10,12,20,18,16,14};
        private final int[] ROTOR_4 = {4,18,14,21,15,25,9,0,24,16,20,8,17,7,23,11,13,5,19,6,10,3,2,12,22,1};
        private final int[] ROTOR_5 = {21,25,1,17,6,8,19,24,20,15,18,3,13,7,11,23,0,22,12,9,16,14,5,4,2,10};

        private int[] rotor = new int[26];
        private final int[] reverse = new int[26];
        private int rotorPosition;
        private int turnoverPoint;

        public Rotor(int rotorType, int rotorPosition, int ringSetting) {
            this.rotorPosition = rotorPosition-1;
            switch(rotorType) {
                case 1:
                    rotor = setInitialPosition(ROTOR_1, ringSetting-1);
                    turnoverPoint = 16;
                    break;
                case 2:
                    rotor = setInitialPosition(ROTOR_2, ringSetting-1);
                    turnoverPoint = 4;
                    break;
                case 3:
                    rotor = setInitialPosition(ROTOR_3, ringSetting-1);
                    turnoverPoint = 21;
                    break;
                case 4:
                    rotor = setInitialPosition(ROTOR_4, ringSetting-1);
                    turnoverPoint = 9;
                    break;
                case 5:
                    rotor = setInitialPosition(ROTOR_5, ringSetting-1);
                    turnoverPoint = 25;
                    break;
            }

            for(int i=0; i<26; i++)
                reverse[rotor[i]] = i;
        }

        private int[] setInitialPosition(int[] rotorType, int ringSetting) {
            int[] output = new int[26];
            for(int i=0; i<26; i++)
                output[i] = (rotorType[(i+26-ringSetting)%26]+ringSetting)%26;
            return output;
        }

        public int transform(int c) {
            return (rotor[(c+26+rotorPosition)%26]-rotorPosition+26)%26;
        }
        public int reverseTransform(int c) {
            return (reverse[(c+26+rotorPosition)%26]-rotorPosition+26)%26;
        }
        public void shift() {
            rotorPosition = (rotorPosition+1)%26;
        }
        public int getRotorPosition() {
            return rotorPosition;
        }
        public void setRotorPosition(int rotorPosition){
            this.rotorPosition = rotorPosition;
        }
        public int getTurnoverPoint() {
            return turnoverPoint;
        }
    }
}
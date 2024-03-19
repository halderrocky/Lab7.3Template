package edu.sdccd.cisc191.ciphers;

import java.util.HashMap;
import java.util.Map;
public class Phonetic {
    public static String printPhoneticEncoded(String phoneticEncodeTestString) {
        //Making new map, sets A to Alpha... Z to Zulu
        Map<Character, String> phoneticEncodeMap = new HashMap<>();
        phoneticEncodeMap.put('A', "Alpha");
        phoneticEncodeMap.put('B', "Bravo");
        phoneticEncodeMap.put('C', "Charlie");
        phoneticEncodeMap.put('D', "Delta");
        phoneticEncodeMap.put('E', "Echo");
        phoneticEncodeMap.put('F', "Foxtrot");
        phoneticEncodeMap.put('G', "Golf");
        phoneticEncodeMap.put('H', "Hotel");
        phoneticEncodeMap.put('I', "India");
        phoneticEncodeMap.put('J', "Juliet");
        phoneticEncodeMap.put('K', "Kilo");
        phoneticEncodeMap.put('L', "Lima");
        phoneticEncodeMap.put('M', "Mike");
        phoneticEncodeMap.put('N', "November");
        phoneticEncodeMap.put('O', "Oscar");
        phoneticEncodeMap.put('P', "Papa");
        phoneticEncodeMap.put('Q', "Quebec");
        phoneticEncodeMap.put('R', "Romeo");
        phoneticEncodeMap.put('S', "Sierra");
        phoneticEncodeMap.put('T', "Tango");
        phoneticEncodeMap.put('U', "Uniform");
        phoneticEncodeMap.put('V', "Victor");
        phoneticEncodeMap.put('W', "Whiskey");
        phoneticEncodeMap.put('X', "X-ray");
        phoneticEncodeMap.put('Y', "Yankee");
        phoneticEncodeMap.put('Z', "Zulu");
//======================================================================================================//
        // StringBuilder to store the encoded string, this is our sentence frame
        StringBuilder phoneticEncodedString = new StringBuilder();
        //abc -> A|B|C, encodes each letter
        //---
        for (char phoneticEncodeLetter : phoneticEncodeTestString.toUpperCase().toCharArray()) {
            if (Character.isLetter(phoneticEncodeLetter)) {
                phoneticEncodedString.append(phoneticEncodeMap.getOrDefault(phoneticEncodeLetter, ""));
                phoneticEncodedString.append(" ");
            } else {
                phoneticEncodedString.append(phoneticEncodeLetter); //If character isn't a letter, just leave it be
            }
        }
        //---
        return phoneticEncodedString.toString(); //print encoded
    } //printPhoneticEncoded close
//======================================================================================================//

    public static String printPhoneticDecoded(String phoneticDecodeTestString) {
        //Making new map, Alpha to A... Zulu to Z
        Map<String, Character> phoneticDecodeMap = new HashMap<>();
        phoneticDecodeMap.put("Alpha", 'A');
        phoneticDecodeMap.put("Bravo", 'B');
        phoneticDecodeMap.put("Charlie", 'C');
        phoneticDecodeMap.put("Delta", 'D');
        phoneticDecodeMap.put("Echo", 'E');
        phoneticDecodeMap.put("Foxtrot", 'F');
        phoneticDecodeMap.put("Golf", 'G');
        phoneticDecodeMap.put("Hotel", 'H');
        phoneticDecodeMap.put("India", 'I');
        phoneticDecodeMap.put("Juliet", 'J');
        phoneticDecodeMap.put("Kilo", 'K');
        phoneticDecodeMap.put("Lima", 'L');
        phoneticDecodeMap.put("Mike", 'M');
        phoneticDecodeMap.put("November", 'N');
        phoneticDecodeMap.put("Oscar", 'O');
        phoneticDecodeMap.put("Papa", 'P');
        phoneticDecodeMap.put("Quebec", 'Q');
        phoneticDecodeMap.put("Romeo", 'R');
        phoneticDecodeMap.put("Sierra", 'S');
        phoneticDecodeMap.put("Tango", 'T');
        phoneticDecodeMap.put("Uniform", 'U');
        phoneticDecodeMap.put("Victor", 'V');
        phoneticDecodeMap.put("Whiskey", 'W');
        phoneticDecodeMap.put("X-ray", 'X');
        phoneticDecodeMap.put("Yankee", 'Y');
        phoneticDecodeMap.put("Zulu", 'Z');
//======================================================================================================//
        // StringBuilder to store the decoded string, this is our sentence frame
        StringBuilder phoneticDecodedString = new StringBuilder();
        // Split the encoded string for every space
        String[] phoneticDecodeWords = phoneticDecodeTestString.split(" ");
        //---
        for (String phoneticDecodeWord : phoneticDecodeWords) {
            if (phoneticDecodeMap.containsKey(phoneticDecodeWord)) {
                phoneticDecodedString.append(phoneticDecodeMap.get(phoneticDecodeWord));

            } else {
                // If the word isn't one of the words in the map, then leave it be
                phoneticDecodedString.append(phoneticDecodeWord);
                phoneticDecodedString.append(' ');
            }
        }
        //---
        return phoneticDecodedString.toString();
    } //printPhoneticDecoded close
}

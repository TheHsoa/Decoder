package com.nstu.substitutioncipher.decryption;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

class AbcDecryptMap {
    private Map<Integer, Integer> abcDecryptMap = new HashMap<>();

    Map<Integer, Integer> getAbcDecryptMap() {
        return abcDecryptMap;
    }

    void clearChars(Set<Integer> abc) {
        for (Integer temp : abc) {
            if (abcDecryptMap.containsKey(temp)) {
                abcDecryptMap.remove(temp);
            }

        }
    }

    boolean addChars(String before, String after) {
        if(!isValidSubstitution(before, after)) return false;
        for(int i = 0; i < before.length(); i++) {
            if(!abcDecryptMap.containsKey((int)before.charAt(i))) {
                abcDecryptMap.put((int) before.charAt(i), (int) after.charAt(i));
            }
        }
        return true;
    }

    private boolean isValidSubstitution(String before, String after) {
        for (int i = 0; i < before.length(); i++) {
            if ((abcDecryptMap.get((int) before.charAt(i)) != null && abcDecryptMap.get((int) before.charAt(i)) != (int) after.charAt(i))
                    || (abcDecryptMap.get((int) before.charAt(i)) == null && abcDecryptMap.containsValue((int) after.charAt(i))))
                return false;
        }
        return true;
    }

    Pattern getWordPattern(String word) {
        String patternString = "^";
        String anyChar = "[а-я]";
        for(int i = 0; i < word.length(); i++) {
            char c = getDecryptChar(word.charAt(i));
            if(c != '0') {
                patternString += c;
            }
            else {
                patternString += anyChar;
            }
        }
        patternString += "$";

        Pattern pattern = Pattern.compile(patternString);
        return pattern;
    }

    private char getDecryptChar(char c) {
        if(abcDecryptMap.containsKey((int) c)) return (char) abcDecryptMap.get((int) c).intValue();
        else return '0';
    }

   // public void addCharsSubstitution(String word,)
}

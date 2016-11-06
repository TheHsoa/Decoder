package com.nstu.substitutioncipher;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by R_A_D on 08.11.2015.
 */
public class Word implements Comparable<Word>{
    private String name;
    private String structure;
    private int length;
    private Set<Integer> abc = new HashSet<>();

    public Set<Integer> getAbc() {
        return abc;
    }

    public Word(String name) {
        this.name = createClearWord(name);
        this.structure = calcStructure();
        this.length = this.name.length();
        createAbc();

    }

    public String getName() {
        return name;
    }

    public String getStructure() {
        return structure;
    }

    public  int getLength() {
        return length;
    }

    private String calcStructure() {
        String structure = "а";
        char index = 'б';
        for(int i = 1; i < this.name.length(); i++) {
            for(int j = 0; j < i; j++) {
                if(name.charAt(i) == name.charAt(j)) {
                    structure+=structure.charAt(j);
                    break;
                }
                if(j == (i-1)) {
                    structure+=index;
                    index++;
                }
            }
        }
        return structure;
    }

    private String createClearWord(String name){
        name = name.replaceAll("-", "");
        name = name.toLowerCase();
        return name;
    }

    private void createAbc() {
        for(int i = 0; i < length; i ++) {
            if(!abc.contains((int) name.charAt(i))) {
                abc.add((int) name.charAt(i));
            }
        }
    }

    public boolean containsWordInWordsSet(Set<Word> s) {
        for (Word word : s) {
            if (name.equals(word.getName())) return true;
        }
        return false;
    }

    @Override
    public int compareTo(Word w) {

        if(w.getLength() == length) {
            return -1;
        }
        return  w.getLength() - length;
    }

    public boolean checkWithRegExp(Pattern pattern) {
        return pattern.matcher(name).matches();
    }
}

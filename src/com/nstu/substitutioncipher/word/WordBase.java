package com.nstu.substitutioncipher.word;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by R_A_D on 07.11.2016.
 */
public abstract class WordBase implements Comparable {
    protected String name;
    protected String structure;
    protected int length;
    protected Set<Integer> abc = new HashSet<>();

    protected WordBase(String name) {
        this.name = createClearWord(name);
        this.structure = calcStructure();
        this.length = this.name.length();
        createAbc();

    }

    public Set<Integer> getAbc() {
        return abc;
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

    protected String calcStructure() {
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

    protected String createClearWord(String name){
        name = name.replaceAll("-", "");
        name = name.toLowerCase();
        return name;
    }

    protected void createAbc() {
        for(int i = 0; i < length; i ++) {
            if(!abc.contains((int) name.charAt(i))) {
                abc.add((int) name.charAt(i));
            }
        }
    }

    public boolean containsWordInWordsSet(Set<WordBase> s) {
        for (WordBase word : s) {
            if (name.equals(word.getName())) return true;
        }
        return false;
    }

    public boolean checkWithRegExp(Pattern pattern) {
        return pattern.matcher(name).matches();
    }
}

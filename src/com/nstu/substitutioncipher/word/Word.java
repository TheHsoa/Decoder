package com.nstu.substitutioncipher.word;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by R_A_D on 08.11.2015.
 */
public class Word extends WordBase {
    public Word(String name) {
        super(name);
    }

    @Override
    public int compareTo(Object o) {
        Word w = (Word)o;

        if(w.length== length) {
            return -1;
        }
        return  w.length - length;
    }
}

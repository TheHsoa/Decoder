package com.nstu.substitutioncipher.word;

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

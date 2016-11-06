package com.nstu.substitutioncipher;

import java.util.*;

/**
 * Created by R_A_D on 26.09.2016.
 */
public class SetOfWords {
    private Set<Word> setOfWords = new TreeSet<>();
    private Set<Integer> abc = new HashSet<>();
    private Set<Word> setOfAbcWords = new TreeSet<>();

    public Set<Integer> getAbc() {
        return abc;
    }

    public Set<Word> getSetOfWords() {

        return setOfWords;
    }

    public Set<Word> getSetOfAbcWords() {
        return setOfAbcWords;
    }

    public SetOfWords(String text) {

        setOfWords = fillWords(text);
        abc = fillAbc(setOfWords);
        setOfAbcWords = fillSetOfAbcWords_WithTotalLength100(setOfWords, abc);
    }

    private TreeSet<Word> fillWords(String text) {
        TreeSet<Word> words = new TreeSet<>();
        int beginIndex = 0;
        int endIndex = text.indexOf(' ', beginIndex);

        while (endIndex > 0) {
            Word word = new Word(text.substring(beginIndex, endIndex));
            if (!word.containsWordInWordsSet(words)) {
                words.add(word);
            }
            beginIndex = endIndex + 1;
            endIndex = text.indexOf(' ', beginIndex);
        }
        Word word = new Word(text.substring(beginIndex, text.length()));
        if (!word.containsWordInWordsSet(words)) {
            words.add(word);
        }
        return words;
    }

    private Set<Integer> fillAbc(Set<Word> words) {
        Set<Integer> abc = new HashSet<>();
        Iterator<Word> iterator = words.iterator();
        while (iterator.hasNext()){
            addCharsInAbc(iterator.next(), abc);
            if (abc.size() >= 32) break;
        }
        return abc;
    }

    private Set<Word> fillSetOfAbcWords(Set<Word> words, Set<Integer> abc) {
        Set<Word> abcWords = new TreeSet<>();
        Set<Integer> bufAbc = new HashSet<>();
        Iterator<Word> iterator = words.iterator();

        while (!bufAbc.equals(abc) && iterator.hasNext()) {
            Word word = iterator.next();
            if(containsChar(word, bufAbc) != 0) {
                addCharsInAbc(word, bufAbc);
                abcWords.add(word);
            }
        }
        return abcWords;
    }

    private int containsChar(Word word, Set<Integer> abc) {
        int num = 0;
        for (int i : word.getAbc()) {
            if (!abc.contains(i)) num++;
        }
        return num;
    }

    private boolean containWord(Word word, Set<Word> abc) {
        Iterator<Word> iterator = abc.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().getName().equals(word.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addCharsInAbc(Word word, Set<Integer> abc) {
        word.getAbc().stream().filter(i -> !abc.contains(i)).forEach(abc::add);
    }

    private int getSumLengthOfWordsInSetOfWords(Set<Word> setOfWords) {
        int length = 0;
        Iterator<Word> iterator = setOfWords.iterator();
        while(iterator.hasNext()) {
            length += iterator.next().getLength();
        }
        return length;
    }

    private Set<Word> fillSetOfAbcWords_WithTotalLength100(Set<Word> words, Set<Integer> abc) {
        if(getSumLengthOfWordsInSetOfWords(words) > 100) {
            Set<Word> abcWords = fillSetOfAbcWords(words, abc);

            Iterator<Word> iterator = words.iterator();
            int sumLength = getSumLengthOfWordsInSetOfWords(abcWords);

            while(sumLength < 100 && iterator.hasNext()) {
                Word word = iterator.next();
                if(!containWord(word, abcWords)) {
                    abcWords.add(word);
                    sumLength += word.getLength();
                }
            }
            return abcWords;
        }
        return words;
    }
}
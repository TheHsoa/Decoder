package com.nstu.substitutioncipher.setofwords;

import com.nstu.substitutioncipher.Vocabulary;
import com.nstu.substitutioncipher.word.Word;
import com.nstu.substitutioncipher.word.WordBase;
import com.nstu.substitutioncipher.word.WordWithStats;

import java.io.IOException;
import java.util.*;

/**
 * Created by R_A_D on 26.09.2016.
 */
public class SetOfWords {
    private Set<WordBase> setOfWords = new TreeSet<>();
    private Set<Integer> abc = new HashSet<>();
    private Set<WordBase> setOfAbcWords = new TreeSet<>();

    private Vocabulary vocabulary = null;

    public Set<Integer> getAbc() {
        return abc;
    }

    public Set<WordBase> getSetOfWords() {

        return setOfWords;
    }

    public Set<WordBase> getSetOfAbcWords() {
        return setOfAbcWords;
    }

    public SetOfWords(String text) {
        text = deleteFirstAndLastWords(text);

        setOfWords = fillWords(text);
        abc = fillAbc(setOfWords);
        setOfAbcWords = fillSetOfAbcWords_WithTotalLength100(setOfWords, abc);
    }

    public SetOfWords(String text, Vocabulary vocabulary) throws IOException {
        text = deleteFirstAndLastWords(text);

        this.vocabulary = vocabulary;
        setOfWords = fillWords(text, vocabulary);
        abc = fillAbc(setOfWords);
        setOfAbcWords = fillSetOfAbcWords_WithTotalLength100(setOfWords, abc);
    }

    private TreeSet<WordBase> fillWords(String text) {
        TreeSet<WordBase> words = new TreeSet<>();
        int beginIndex = 0;
        int endIndex = text.indexOf(' ', beginIndex);

        while (endIndex > 0) {
            WordBase word = new Word(text.substring(beginIndex, endIndex));
            if (!word.containsWordInWordsSet(words)) {
                words.add(word);
            }
            beginIndex = endIndex + 1;
            endIndex = text.indexOf(' ', beginIndex);
        }
        WordBase word = new Word(text.substring(beginIndex, text.length()));
        if (!word.containsWordInWordsSet(words)) {
            words.add(word);
        }
        return words;
    }

    private TreeSet<WordBase> fillWords(String text, Vocabulary vocabulary) throws IOException {
        TreeSet<WordBase> words = new TreeSet<>();
        int beginIndex = 0;
        int endIndex = text.indexOf(' ', beginIndex);

        while (endIndex > 0) {
            WordBase word = new WordWithStats(text.substring(beginIndex, endIndex), vocabulary);
            if (!word.containsWordInWordsSet(words)) {
                words.add(word);
            }
            beginIndex = endIndex + 1;
            endIndex = text.indexOf(' ', beginIndex);
        }
        WordBase word = new WordWithStats(text.substring(beginIndex, text.length()), vocabulary);
        if (!word.containsWordInWordsSet(words)) {
            words.add(word);
        }
        return words;
    }

    private Set<Integer> fillAbc(Set<WordBase> words) {
        Set<Integer> abc = new HashSet<>();
        Iterator<WordBase> iterator = words.iterator();
        while (iterator.hasNext()){
            addCharsInAbc(iterator.next(), abc);
            if (abc.size() >= 32) break;
        }
        return abc;
    }

    private Set<WordBase> fillSetOfAbcWords(Set<WordBase> words, Set<Integer> abc) {
        Set<WordBase> abcWords = new TreeSet<>();
        Set<Integer> bufAbc = new HashSet<>();
        Iterator<WordBase> iterator = words.iterator();

        while (!bufAbc.equals(abc) && iterator.hasNext()) {
            WordBase word = iterator.next();
            if(containsChar(word, bufAbc) != 0) {
                addCharsInAbc(word, bufAbc);
                abcWords.add(word);
            }
        }
        return abcWords;
    }

    private int containsChar(WordBase word, Set<Integer> abc) {
        int num = 0;
        for (int i : word.getAbc()) {
            if (!abc.contains(i)) num++;
        }
        return num;
    }

    private boolean containWord(WordBase word, Set<WordBase> abc) {
        Iterator<WordBase> iterator = abc.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().getName().equals(word.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addCharsInAbc(WordBase word, Set<Integer> abc) {
        word.getAbc().stream().filter(i -> !abc.contains(i)).forEach(abc::add);
    }

    private int getSumLengthOfWordsInSetOfWords(Set<WordBase> setOfWords) {
        int length = 0;
        Iterator<WordBase> iterator = setOfWords.iterator();
        while(iterator.hasNext()) {
            length += iterator.next().getLength();
        }
        return length;
    }

    private Set<WordBase> fillSetOfAbcWords_WithTotalLength100(Set<WordBase> words, Set<Integer> abc) {
        if(getSumLengthOfWordsInSetOfWords(words) > 100) {
            Set<WordBase> abcWords = fillSetOfAbcWords(words, abc);

            Iterator<WordBase> iterator = words.iterator();
            int sumLength = getSumLengthOfWordsInSetOfWords(abcWords);

            while(sumLength < 100 && iterator.hasNext()) {
                WordBase word = iterator.next();
                if(!containWord(word, abcWords)) {
                    abcWords.add(word);
                    sumLength += word.getLength();
                }
            }
            return abcWords;
        }
        return words;
    }

    private String deleteFirstAndLastWords(String text) {
        text = text.trim();
        if (text.split(" ").length >= 3)
            return text.substring(text.indexOf(' ') + 1, text.lastIndexOf(' '));
        return text;
    }
}
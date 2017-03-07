package com.nstu.substitutioncipher.setofwords;

import com.nstu.substitutioncipher.Vocabulary;
import com.nstu.substitutioncipher.word.Word;
import com.nstu.substitutioncipher.word.WordBase;
import com.nstu.substitutioncipher.word.WordWithStats;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SetOfWords {
    private Set<WordBase> setOfWords = new TreeSet<>();
    private Set<Integer> abc = new HashSet<>();
    private Set<WordBase> setOfAbcWords = new TreeSet<>();
    private Vocabulary vocabulary = null;


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
        setOfAbcWords = fillSetOfAbcWords_WithTotalLength100();
    }

    public SetOfWords(String text, Vocabulary vocabulary) throws IOException {
        this.vocabulary = vocabulary;

        text = deleteFirstAndLastWords(text);

        setOfWords = fillWords(text, vocabulary);
        abc = fillAbc(setOfWords);
        setOfAbcWords = fillSetOfAbcWords_WithTotalLength100();
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
        for (WordBase word : words) {
            addCharsInAbc(word, abc);
            if (abc.size() >= 32) break;
        }
        return abc;
    }

    private Set<WordBase> fillSetOfAbcWords(Set<WordBase> words, Set<Integer> abc) {
        Set<WordBase> abcWords = new TreeSet<>();
        Map<Integer, Integer> bufAbcMap = new HashMap<>();

        words.stream().filter(x -> !bufAbcMap.keySet().equals(abc)).forEach(x -> addWordIfExtends(x, abcWords, bufAbcMap));

        return abcWords;
    }

    private void addWordIfExtends(WordBase word, Set<WordBase> abcWords, Map<Integer, Integer> bufAbcMap) {
        if(containsChar(word, bufAbcMap)) abcWords.add(word);
        addCharsInAbc(word, bufAbcMap);
    }

    private boolean containsChar(WordBase word, Map<Integer, Integer> abc) {
        for (int i : word.getAbc()) {
            if(!abc.keySet().contains(i) || abc.get(i) < 2) return true;
        }
        return false;
    }

    private boolean containWord(WordBase word, Set<WordBase> abc) {
        for (WordBase anAbc : abc) {
            if (anAbc.getName().equals(word.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addCharsInAbc(WordBase word, Set<Integer> abc) {
        word.getAbc().stream().filter(i -> !abc.contains(i)).forEach(abc::add);
    }

    private void addCharsInAbc(WordBase word, Map<Integer, Integer> abc) {
        word.getAbc().forEach(x -> abc.put(x, abc.containsKey(x) ? abc.get(x) + 1 : 1));
    }

    private int getSumLengthOfWordsInSetOfWords(Set<WordBase> setOfWords) {
        int length = 0;
        for (WordBase setOfWord : setOfWords) {
            length += setOfWord.getLength();
        }
        return length;
    }

    private Set<WordBase> fillSetOfAbcWords_WithTotalLength100() {
        Set<WordBase> wordsWithVocabulary = getSetOfWordsWithVocabulary();
        if(getSumLengthOfWordsInSetOfWords(wordsWithVocabulary) > 100) {
            Set<WordBase> abcWords = fillSetOfAbcWords(wordsWithVocabulary, abc);

            Iterator<WordBase> iterator = wordsWithVocabulary.iterator();
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
        return wordsWithVocabulary;
    }

    private Set<WordBase> getSetOfWordsWithVocabulary() {
        return setOfWords.stream().map(x -> (WordWithStats)x).filter(x -> x.getStructureStats() != 0).collect(Collectors.toSet());
    }

    private String deleteFirstAndLastWords(String text) {
        text = text.trim();
        if (text.split(" ").length >= 3)
            return text.substring(text.indexOf(' ') + 1, text.lastIndexOf(' '));
        return text;
    }
}
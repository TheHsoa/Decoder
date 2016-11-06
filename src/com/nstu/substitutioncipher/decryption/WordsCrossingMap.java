package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.Word;

import java.util.*;

/**
 * Created by R_A_D on 02.10.2016.
 */
public class WordsCrossingMap {
    private Map<String, ArrayList<String>> wordsCrossingMap = new HashMap<>();

    public WordsCrossingMap(Set<Word> wordsSet) {
        wordsCrossingMap = generateWordsCrossingMap(wordsSet);
    }

    public Map<String, ArrayList<String>> getWordsCrossingMap() {
        return wordsCrossingMap;
    }

    private Map generateWordsCrossingMap(Set<Word> wordsSet) {
        Map<String, ArrayList<String>> wordsCrossingMap = new HashMap<>();
        Iterator<Word> iterator = wordsSet.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            wordsCrossingMap.put(word.getName(), crossingWordsInSet(word, wordsSet));
        }
        return wordsCrossingMap;
    }

    private ArrayList<String> crossingWordsInSet(Word word, Set<Word> wordsSet) {
        ArrayList<String> crossingWords = new ArrayList<>();
        Iterator<Word> iterator = wordsSet.iterator();
        while (iterator.hasNext()) {
            Word setWord = iterator.next();
            if(!setWord.getName().equals(word.getName()) && crossWords(setWord, word)) {
                crossingWords.add(setWord.getName());
            }
        }
        return crossingWords;
    }

    private boolean crossWords(Word firstWord, Word secondWord) {
        Iterator<Integer> iterator = firstWord.getAbc().iterator();
        while (iterator.hasNext()) {
            if(secondWord.getAbc().contains(iterator.next())) {
                return true;
            }
        }
        return false;
    }

}
package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.word.WordBase;

import java.util.*;


class WordsCrossingMap {
    private Map<String, ArrayList<String>> wordsCrossingMap = new HashMap<>();

    WordsCrossingMap(Set<WordBase> wordsSet) {
        wordsCrossingMap = generateWordsCrossingMap(wordsSet);
    }

    Map<String, ArrayList<String>> getWordsCrossingMap() {
        return wordsCrossingMap;
    }

    private Map<String, ArrayList<String>> generateWordsCrossingMap(Set<WordBase> wordsSet) {
        Map<String, ArrayList<String>> wordsCrossingMap = new HashMap<>();
        for (WordBase word : wordsSet) {
            wordsCrossingMap.put(word.getName(), crossingWordsInSet(word, wordsSet));
        }
        return wordsCrossingMap;
    }

    private ArrayList<String> crossingWordsInSet(WordBase word, Set<WordBase> wordsSet) {
        ArrayList<String> crossingWords = new ArrayList<>();
        for (WordBase setWord : wordsSet) {
            if (!setWord.getName().equals(word.getName()) && crossWords(setWord, word)) {
                crossingWords.add(setWord.getName());
            }
        }
        return crossingWords;
    }

    private boolean crossWords(WordBase firstWord, WordBase secondWord) {
        for (Integer integer : firstWord.getAbc()) {
            if (secondWord.getAbc().contains(integer)) {
                return true;
            }
        }
        return false;
    }

}
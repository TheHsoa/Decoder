package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.word.Word;
import com.nstu.substitutioncipher.word.WordBase;

import java.util.*;

/**
 * Created by R_A_D on 02.10.2016.
 */
public class WordsCrossingMap {
    private Map<String, ArrayList<String>> wordsCrossingMap = new HashMap<>();

    public WordsCrossingMap(Set<WordBase> wordsSet) {
        wordsCrossingMap = generateWordsCrossingMap(wordsSet);
    }

    public Map<String, ArrayList<String>> getWordsCrossingMap() {
        return wordsCrossingMap;
    }

    private Map generateWordsCrossingMap(Set<WordBase> wordsSet) {
        Map<String, ArrayList<String>> wordsCrossingMap = new HashMap<>();
        Iterator<WordBase> iterator = wordsSet.iterator();
        while (iterator.hasNext()) {
            WordBase word = iterator.next();
            wordsCrossingMap.put(word.getName(), crossingWordsInSet(word, wordsSet));
        }
        return wordsCrossingMap;
    }

    private ArrayList<String> crossingWordsInSet(WordBase word, Set<WordBase> wordsSet) {
        ArrayList<String> crossingWords = new ArrayList<>();
        Iterator<WordBase> iterator = wordsSet.iterator();
        while (iterator.hasNext()) {
            WordBase setWord = iterator.next();
            if(!setWord.getName().equals(word.getName()) && crossWords(setWord, word)) {
                crossingWords.add(setWord.getName());
            }
        }
        return crossingWords;
    }

    private boolean crossWords(WordBase firstWord, WordBase secondWord) {
        Iterator<Integer> iterator = firstWord.getAbc().iterator();
        while (iterator.hasNext()) {
            if(secondWord.getAbc().contains(iterator.next())) {
                return true;
            }
        }
        return false;
    }

}
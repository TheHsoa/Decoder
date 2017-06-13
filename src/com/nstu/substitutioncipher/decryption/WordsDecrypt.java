package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.vocabularies.IVocabulary;
import com.nstu.substitutioncipher.word.Word;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

class WordsDecrypt {
    private Map<String,Map<String,Integer>> WordsDecryptMap;
    private Map<String, List<String>> WordsVocabulary;
    private Map<String, Set<Integer>> WordsNewChars;
    private WordsCrossingMap CrossingMap;
    private AbcDecryptMap AbcDecryptMap;

    WordsDecrypt(SetOfWords words, IVocabulary vocabulary) throws IOException {
        WordsDecryptMap = new HashMap<>();
        WordsVocabulary = new HashMap<>();
        WordsNewChars = new HashMap<>();
        CrossingMap = new WordsCrossingMap(words.getSetOfAbcWords());
        AbcDecryptMap = new AbcDecryptMap();

        Iterator<WordBase> iterator = words.getSetOfAbcWords().iterator();

        Set<Integer> allChars = new HashSet<>();

        while (iterator.hasNext()) {
            WordBase word = iterator.next();

            Set<Integer> tempChars = new HashSet<>();

            word.getAbc().stream().filter(i -> !allChars.contains(i)).forEach(tempChars::add);

            word.getAbc().stream().filter(i -> !allChars.contains(i)).forEach(allChars::add);

            WordsNewChars.put(word.getName(), tempChars);

            if(!WordsDecryptMap.containsKey(word.getStructure())) {

                Map<String,Integer> temp = new HashMap<>();
                temp.put(word.getName(), -1);
                WordsDecryptMap.put(word.getStructure(),temp);

                WordsVocabulary.put(word.getStructure(), vocabulary.getWordsInStructure(word.getStructure()));

            }
            else {
                if(!WordsDecryptMap.get(word.getStructure()).containsKey(word.getName())) {
                    WordsDecryptMap.get(word.getStructure()).put(word.getName(), -1);
                }
            }
        }
    }

    private boolean haveWordInVocabulary(WordBase word) {
        return WordsVocabulary.get(word.getStructure()).size() > (WordsDecryptMap.get(word.getStructure()).get(word.getName())) + 1;
    }

    boolean nextWord(WordBase word) {

        clearWordInAbcMap(word);

        while (haveWordInVocabulary(word)) {

            WordsDecryptMap.get(word.getStructure()).put(word.getName(), WordsDecryptMap.get(word.getStructure()).get(word.getName()) + 1);

         //   System.out.println(word.getName() + " - " + WordsDecryptMap.get(word.getStructure()).get(word.getName()));

            if(!haveThatVocabularyWordIdAnotherWords(word)
                    && AbcDecryptMap.addChars(word.getName(), WordsVocabulary
                    .get(word.getStructure())
                    .get(WordsDecryptMap.get(word.getStructure()).
                            get(word.getName())))) {
                return true;
            }
            else {
                clearWordInAbcMap(word);
            }
        }

        clearWordInAbcMap(word);
        clearDecryptWord(word);

        return false;
    }

    private void clearDecryptWord(WordBase word) {
        WordsDecryptMap.get(word.getStructure()).put(word.getName(), -1);
    }

    private void clearWordInAbcMap(WordBase word) {
        AbcDecryptMap.clearChars(WordsNewChars.get(word.getName()));
    }

    Map<Integer, Integer> getAbcDecryptMap() {
        return AbcDecryptMap.getAbcDecryptMap();
    }

    private boolean isValidWord(Pattern patternWord, String structure) {
        for (String s : WordsVocabulary.get(structure)) {
            if (patternWord.matcher(s).matches()) return true;
        }
        return false;
    }

    private boolean haveThatVocabularyWordIdAnotherWords(WordBase word) {

        for (String temp : WordsDecryptMap.get(word.getStructure()).keySet()) {
            if (!temp.equals(word.getName()) && Objects.equals(WordsDecryptMap.get(word.getStructure()).get(word.getName()), WordsDecryptMap.get(word.getStructure()).get(temp))) {
                return true;
            }
        }

        return false;
    }

    double getAverageDebt() {
        double AverageDebt = 0;
        int numOfWords = 0;
        for (String structure : WordsDecryptMap.keySet()) {
            int wordsInVocabulary;
            if (WordsVocabulary.containsKey(structure)) {
                wordsInVocabulary = WordsVocabulary.get(structure).size() * WordsDecryptMap.get(structure).keySet().size();
            } else wordsInVocabulary = 0;

            for (String word : WordsDecryptMap.get(structure).keySet()) {
                AverageDebt += wordsInVocabulary == 0 ? 1 : (WordsDecryptMap.get(structure).get(word) + 1) / wordsInVocabulary;
                numOfWords++;
            }
        }
        return AverageDebt / numOfWords;
    }

    double getAverageWordsInVocabulary() {
        double WordsInVocabulary = 0;
        int numOfWords = 0;

        for (String structure : WordsDecryptMap.keySet()) {
            int words = WordsDecryptMap.get(structure).keySet().size();

            if (WordsVocabulary.containsKey(structure)) {
                WordsInVocabulary += WordsVocabulary.get(structure).size() * words;
            }

            numOfWords += words;
        }

        return WordsInVocabulary / numOfWords;
    }
}

package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.Vocabulary;
import com.nstu.substitutioncipher.word.Word;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by R_A_D on 16.10.2016.
 */
public class WordsDecrypt {
    private Map<String,Map<String,Integer>> WordsDecryptMap;
    private Map<String, List<String>> WordsVocabulary;
    private Map<String, Set<Integer>> WordsNewChars;
    private WordsCrossingMap CrossingMap;
    private AbcDecryptMap AbcDecryptMap;

    public Map<String, Set<Integer>> getWordsNewChars() {
        return WordsNewChars;
    }

    public Map<String, List<String>> getWordsVocabulary() {

        return WordsVocabulary;
    }

    public WordsDecrypt(SetOfWords words, Vocabulary vocabulary) throws IOException {
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
                temp.put(word.getName(), 0);
                WordsDecryptMap.put(word.getStructure(),temp);

                WordsVocabulary.put(word.getStructure(), vocabulary.getWordsInStructure(word.getStructure()));

            }
            else {
                if(!WordsDecryptMap.get(word.getStructure()).containsKey(word.getName())) {
                    WordsDecryptMap.get(word.getStructure()).put(word.getName(), 0);
                }
            }
        }
    }

    public boolean haveWordInVocabulary(WordBase word) {
        if(WordsVocabulary.get(word.getStructure()).size() <= (WordsDecryptMap.get(word.getStructure()).get(word.getName()))) {
            return false;
        }
        return true;
    }

    public boolean nextWord(WordBase word) {

        while (haveWordInVocabulary(word)) {

            if(!haveThatVocabularyWordIdAnotherWords(word)
                    && AbcDecryptMap.addChars(word.getName(), WordsVocabulary
                    .get(word.getStructure())
                    .get(WordsDecryptMap.get(word.getStructure()).
                            get(word.getName())))) {

                List<String> crossing = CrossingMap.getWordsCrossingMap().get(word.getName());

                if(crossing.isEmpty()) {
                    return true;
                }

                for (int i = 0; i < crossing.size(); i++) {
                    if (isValidWord(AbcDecryptMap.getWordPattern(crossing.get(i)), new Word(crossing.get(i)).getStructure())) {
                        return true;
                    }
                }
                AbcDecryptMap.clearChars(WordsNewChars.get(word.getName()));
            }

            WordsDecryptMap.get(word.getStructure()).put(word.getName(), WordsDecryptMap.get(word.getStructure()).get(word.getName()) + 1);
        }
        clearDecryptWord(word);
       // AbcDecryptMap.clearChars(WordsNewChars.get(word.getName()));
        return false;
    }

    public void clearDecryptWord(WordBase word) {
        WordsDecryptMap.get(word.getStructure()).put(word.getName(), 0);
    }

    public void clearWordInAbcMap(WordBase word) {
        AbcDecryptMap.clearChars(WordsNewChars.get(word.getName()));
    }

    public  Map<Integer, Integer> getAbcDecryptMap() {
        return AbcDecryptMap.getAbcDecryptMap();
    }

    public boolean isValidWord(Pattern patternWord, String structure) {
        Iterator<String> iterator = WordsVocabulary.get(structure).iterator();
        while(iterator.hasNext()) {
            if(patternWord.matcher(iterator.next()).matches()) return true;
        }
        return false;
    }

    private boolean haveThatVocabularyWordIdAnotherWords(WordBase word) {

        Iterator<String> iterator = WordsDecryptMap.get(word.getStructure()).keySet().iterator();

        while (iterator.hasNext()) {
            String temp = iterator.next();
            if (!temp.equals(word.getName()) && WordsDecryptMap.get(word.getStructure()).get(word.getName()) == WordsDecryptMap.get(word.getStructure()).get(temp)) {
                return true;
            }
        }

        return false;
    }

    public double getAverageDebt() {
        double AverageDebt = 0;
        int numOfWords = 0;
        Iterator<String> structureIterator = WordsDecryptMap.keySet().iterator();
        while (structureIterator.hasNext()) {
            String structure = structureIterator.next();

            int wordsInVocabulary;
            if(WordsVocabulary.containsKey(structure)) {
                wordsInVocabulary = WordsVocabulary.get(structure).size() * WordsDecryptMap.get(structure).keySet().size();
            }
            else wordsInVocabulary = 0;

            Iterator<String> wordIterator = WordsDecryptMap.get(structure).keySet().iterator();
            while (wordIterator.hasNext()) {
                String word = wordIterator.next();
                AverageDebt += wordsInVocabulary == 0 ? 1 : (WordsDecryptMap.get(structure).get(word) + 1) / wordsInVocabulary;
                numOfWords ++;
            }
        }
        return AverageDebt / numOfWords;
    }

    public double getAverageWordsInVocabulary() {
        double WordsInVocabulary = 0;
        int numOfWords = 0;

        Iterator<String> structureIterator = WordsDecryptMap.keySet().iterator();
        while (structureIterator.hasNext()) {
            String structure = structureIterator.next();

            int words = WordsDecryptMap.get(structure).keySet().size();

            if(WordsVocabulary.containsKey(structure)) {
                WordsInVocabulary += WordsVocabulary.get(structure).size() * words;
            }

            numOfWords += words;
        }

        return WordsInVocabulary / numOfWords;
    }
}

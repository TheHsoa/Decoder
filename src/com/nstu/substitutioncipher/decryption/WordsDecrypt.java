package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.SetOfWords;
import com.nstu.substitutioncipher.Vocabulary;
import com.nstu.substitutioncipher.Word;

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

        Iterator<Word> iterator = words.getSetOfAbcWords().iterator();

        Set<Integer> allChars = new HashSet<>();

        while (iterator.hasNext()) {
            Word word = iterator.next();

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

    public boolean haveWordInVocabulary(Word word) {
        if(WordsVocabulary.get(word.getStructure()).size() <= (WordsDecryptMap.get(word.getStructure()).get(word.getName()))) {
            return false;
        }
        return true;
    }

    public boolean nextWord(Word word) {

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

    public void clearDecryptWord(Word word) {
        WordsDecryptMap.get(word.getStructure()).put(word.getName(), 0);
    }

    public void clearWordInAbcMap(Word word) {
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

    private boolean haveThatVocabularyWordIdAnotherWords(Word word) {

        Iterator<String> iterator = WordsDecryptMap.get(word.getStructure()).keySet().iterator();

        while (iterator.hasNext()) {
            String temp = iterator.next();
            if (!temp.equals(word.getName()) && WordsDecryptMap.get(word.getStructure()).get(word.getName()) == WordsDecryptMap.get(word.getStructure()).get(temp)) {
                return true;
            }
        }

        return false;
    }
}

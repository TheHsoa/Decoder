package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.TextStandardize;
import com.nstu.substitutioncipher.Vocabulary;
import com.nstu.substitutioncipher.word.Word;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.*;
import java.util.*;

/**
 * Created by R_A_D on 01.10.2016.
 */
public class Decrypt {

    private final int maxIterationForText = 10000;
    public int iterations = 0;
    public double averageDept = 0;
    public double averageWordsInVocabulary;

    public void DecryptFromFileToFile(File inFile, File outFile, Vocabulary vocabulary) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        String tempText;
        String text = "";

        while((tempText = reader.readLine()) != null) {
            text += tempText;
        }
        reader.close();

        writer.write(SubstitutionCipherDecrypt(text, vocabulary).toUpperCase());

        writer.flush();
        writer.close();
    }

    public String SubstitutionCipherDecrypt(String text, Vocabulary vocabulary) throws IOException {
        text = TextStandardize.convertToStandardText(text);

        if(text != "") {

            SetOfWords setOfWords = new SetOfWords(text, vocabulary);

            Map<String, String> wordsMap = DecipherWords(setOfWords, vocabulary);
            if(wordsMap == null) {
                return "Не удалось раскрыть текст";
            }

            return SubstituteWordsMapInText(wordsMap, text.toLowerCase());
        }
        return "";
    }

    protected Map<Integer, Integer> DecipherAbc(SetOfWords setOfWords,  Vocabulary vocabulary) {

        WordsDecrypt wordsDecrypt;
        try {
            wordsDecrypt = new WordsDecrypt(setOfWords, vocabulary);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        List<WordBase> words = new ArrayList<>(setOfWords.getSetOfAbcWords());

        words.sort((o1, o2) -> o1.compareTo(o2));

        int i = 0;

        while(i < words.size()) {
            if(iterations == maxIterationForText) {
                averageDept = wordsDecrypt.getAverageDebt();
                averageWordsInVocabulary = wordsDecrypt.getAverageWordsInVocabulary();
                return null;
            }

            iterations ++;

            if(i < 0)
                return null;

            WordBase word = words.get(i);

            if(!wordsDecrypt.nextWord(word)) {
                i--;
            }
            else {
                i++;
            }
        }
        averageDept = wordsDecrypt.getAverageDebt();
        averageWordsInVocabulary = wordsDecrypt.getAverageWordsInVocabulary();

        return wordsDecrypt.getAbcDecryptMap();
    }

    public Map<String, String> DecipherWords(SetOfWords setOfWords,  Vocabulary vocabulary) {
        Map<String, String> wordsMap = new HashMap<>();
        Map<Integer, Integer> abcMap = DecipherAbc(setOfWords, vocabulary);
        if(abcMap == null) {
            return null;
        }

        Iterator<WordBase> iterator = setOfWords.getSetOfWords().iterator();

        while (iterator.hasNext()) {
            String word = iterator.next().getName();
            wordsMap.put(word, SubstituteAbcMapInWord(abcMap, word));
        }
        return wordsMap;
    }

    private String SubstituteAbcMapInWord(Map<Integer, Integer> abcMap, String word) {
        String newWord = "";

        for(int i = 0; i < word.length(); i ++) {
            newWord += (char) abcMap.get((int)word.charAt(i)).intValue();
        }
        return newWord;
    }

    private String SubstituteWordsMapInText(Map<String, String> wordsMap, String text) {
        String outText = "";

        int beginIndex = 0;
        int endIndex;
        while((endIndex = text.indexOf(' ', beginIndex)) > 0) {
            String word = text.substring(beginIndex, endIndex);
            outText += wordsMap.get(word) + ' ';
            beginIndex = endIndex + 1;
        }

        String word = text.substring(beginIndex, text.length());
        outText += wordsMap.get(word) + ' ';

        return outText;
    }
}
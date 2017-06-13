package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.TextStandardize;
import com.nstu.substitutioncipher.vocabularies.IVocabulary;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.*;
import java.util.*;

public class Decrypt {

    int iterations = 0;
    double averageDepth = 0;
    double averageWordsInVocabulary;
    long operationTime;
    private int numOfCharsInWords;

    Decrypt(int numOfCharsInWords) {
        this.numOfCharsInWords = numOfCharsInWords;
    }

    public void DecryptFromFileToFile(File inFile, File outFile, IVocabulary vocabulary) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        String tempText;
        StringBuilder text = new StringBuilder();

        while((tempText = reader.readLine()) != null) {
            text.append(tempText);
        }
        reader.close();

        writer.write(SubstitutionCipherDecrypt(text.toString(), vocabulary).toUpperCase());

        writer.flush();
        writer.close();
    }

    private String SubstitutionCipherDecrypt(String text, IVocabulary vocabulary) throws IOException {
        text = TextStandardize.convertToStandardText(text);

        if(!Objects.equals(text, "")) {

            SetOfWords setOfWords = new SetOfWords(text, vocabulary, numOfCharsInWords);

            Map<String, String> wordsMap = DecipherWords(setOfWords, vocabulary);
            if(wordsMap == null) {
                return "Не удалось раскрыть текст";
            }

            return SubstituteWordsMapInText(wordsMap, text.toLowerCase());
        }
        return "";
    }

    Map<Integer, Integer> DecipherAbc(SetOfWords setOfWords, IVocabulary vocabulary) {

        long startTime = System.currentTimeMillis();

        WordsDecrypt wordsDecrypt;
        try {
            wordsDecrypt = new WordsDecrypt(setOfWords, vocabulary);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        List<WordBase> words = new ArrayList<>(setOfWords.getSetOfAbcWords());

        words.sort(Comparable::compareTo);

        int i = 0;

        int maxDepth = 0;

        Map<Integer, Integer> maxDepthAbcDecryptMap = new HashMap<>();

        while(i < words.size()) {
            int maxIterationForText = 10000;
            if(iterations == maxIterationForText) {
                averageDepth = wordsDecrypt.getAverageDebt();
                averageWordsInVocabulary = wordsDecrypt.getAverageWordsInVocabulary();
                operationTime = System.currentTimeMillis() - startTime;
                return maxDepthAbcDecryptMap;
            }

            iterations++;

            if(i < 0) {
                averageDepth = wordsDecrypt.getAverageDebt();
                averageWordsInVocabulary = wordsDecrypt.getAverageWordsInVocabulary();
                operationTime = System.currentTimeMillis() - startTime;
                return maxDepthAbcDecryptMap;
            }

            WordBase word = words.get(i);

            if(!wordsDecrypt.nextWord(word)) {
                i--;
            }
            else {
                i++;
                if(maxDepth < i) {
                    maxDepth = i;
                    maxDepthAbcDecryptMap.clear();
                    maxDepthAbcDecryptMap.putAll(wordsDecrypt.getAbcDecryptMap());
                }
            }
        }
        averageDepth = wordsDecrypt.getAverageDebt();
        averageWordsInVocabulary = wordsDecrypt.getAverageWordsInVocabulary();
        operationTime = System.currentTimeMillis() - startTime;

        return wordsDecrypt.getAbcDecryptMap();
    }

    private Map<String, String> DecipherWords(SetOfWords setOfWords, IVocabulary vocabulary) {
        Map<String, String> wordsMap = new HashMap<>();
        Map<Integer, Integer> abcMap = DecipherAbc(setOfWords, vocabulary);
        if(abcMap == null) {
            return null;
        }

        for (WordBase wordBase : setOfWords.getSetOfWords()) {
            String word = wordBase.getName();
            wordsMap.put(word, SubstituteAbcMapInWord(abcMap, word));
        }
        return wordsMap;
    }

    private String SubstituteAbcMapInWord(Map<Integer, Integer> abcMap, String word) {
        StringBuilder newWord = new StringBuilder();

        for(int i = 0; i < word.length(); i ++) {
            newWord.append((char) abcMap.get((int) word.charAt(i)).intValue());
        }
        return newWord.toString();
    }

    private String SubstituteWordsMapInText(Map<String, String> wordsMap, String text) {
        StringBuilder outText = new StringBuilder();

        int beginIndex = 0;
        int endIndex;
        while((endIndex = text.indexOf(' ', beginIndex)) > 0) {
            String word = text.substring(beginIndex, endIndex);
            outText.append(wordsMap.get(word)).append(' ');
            beginIndex = endIndex + 1;
        }

        String word = text.substring(beginIndex, text.length());
        outText.append(wordsMap.get(word)).append(' ');

        return outText.toString();
    }
}
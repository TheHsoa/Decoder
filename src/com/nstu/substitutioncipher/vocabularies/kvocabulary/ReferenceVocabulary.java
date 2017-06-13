package com.nstu.substitutioncipher.vocabularies.kvocabulary;

import com.nstu.substitutioncipher.vocabularies.IVocabulary;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.*;
import java.util.*;

public class ReferenceVocabulary implements IVocabulary{
    private String name;

    public ReferenceVocabulary(String name) {this.name = name; }

    @Override
    public List<String> getWordsInStructure(String structure) throws IOException {
        List<String> words = new ArrayList<>();

        String lenPath = name + "\\" + 'w' + structure.length() + ".txt";

        File wordsWithStructureLenFile = new File(lenPath);

        if(!wordsWithStructureLenFile.exists()) return words;

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(wordsWithStructureLenFile), "Cp1251"));

        reader.readLine();
        reader.readLine();

        structure = structure.toUpperCase();

        KStructure kStructure = getKStructure(reader.readLine(), structure);

        if(kStructure == null) return words;

        Map<Long, KWord> kWords = geKWords(reader.readLine());

        words = getWordsByKStructureInKWordsMap(kWords, kStructure);

        return words;
    }

    @Override
    public long getNumberOfWordsInStructure(String structure) throws IOException {
        return getWordsInStructure(structure).size();
    }

    @Override
    public boolean inVocabulary(WordBase word) {
        return false;
    }

    private KStructure getKStructure(String structures, String structure) {
        int structureIndex = structures.indexOf(structure);

        if(structureIndex == -1) return null;

        int structureAddressIndex = structureIndex + structure.length() + 1;

        return new KStructure(structure, Integer.parseInt(structures.substring(structureAddressIndex,structures.indexOf(' ', structureAddressIndex))));
    }

    private HashMap<Long, KWord> geKWords(String words) {
        HashMap<Long, KWord> kWords = new HashMap<>();

        int index = 0;

        int wordAddressIterator = 0;

        while (index < words.length()) {
            int bufIndex = words.indexOf(' ', index);
            String word = words.substring(index, bufIndex);

            for(int i = 0; i < 2; i ++) {
                index = words.indexOf(' ', bufIndex) + 1;
                bufIndex = words.indexOf(' ', index);
            }

            long nextWordAddress = Long.parseLong(words.substring(index, bufIndex));

            kWords.put((long) wordAddressIterator, new KWord(word, wordAddressIterator, nextWordAddress));

            index = bufIndex + 1;

            wordAddressIterator ++;
        }

        return kWords;
    }

    private ArrayList<String> getWordsByKStructureInKWordsMap(Map<Long, KWord> kWords, KStructure kStructure) {
        ArrayList<String> words = new ArrayList<>();

        Long currentAddress = kStructure.getAddress();

        while(currentAddress != -1) {
            KWord word = kWords.get(currentAddress);
            words.add(word.getName());
            currentAddress = word.getNextWordAddress();
        }

        return words;
    }
}

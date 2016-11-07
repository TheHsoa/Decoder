package com.nstu.substitutioncipher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by R_A_D on 01.02.2016.
 */
public class Statistic {

    private String vocabularyName;
    private HashMap<Integer,Integer> wordStat;
    private HashMap<Integer,Integer> structStat;
    private HashMap<Integer,Integer> easyStructStat;
    private HashMap<Integer,Integer> otherStructStat;

    public Statistic(String vocabularyName) {
        this.vocabularyName = vocabularyName;
        setStructStat();
        setAllStat();
    }

    private void setStructStat() {
        structStat = new HashMap<>();
        File[] filesLen = (new File(vocabularyName)).listFiles();
        for (File fileLen : filesLen) {
            int i = Integer.parseInt(fileLen.getName().replaceAll(".txt", ""));
            structStat.put(i, fileLen.list().length);
        }
    }

    private void setAllStat() {
        wordStat = new  HashMap<>();
        easyStructStat = new HashMap<>();
        otherStructStat = new HashMap<>();
        File[] filesLen = (new File(vocabularyName)).listFiles();
        for (File fileLen : filesLen) {
            int i = Integer.parseInt(fileLen.getName().replaceAll(".txt", ""));
            File[] filesStruct = fileLen.listFiles();
            int wordsNum = 0;
            int easyStructWordsNum = 0;
            for(File fileStruct : filesStruct) {
                int words = (int) (fileStruct.length() / (Integer.parseInt(fileLen.getName()) + 2));
                wordsNum += words;
                if(fileStruct.getName().equals(getSimpleStructure(i-1) + ".txt")) easyStructWordsNum = words;
            }
            wordStat.put(i, wordsNum);
            easyStructStat.put(i, easyStructWordsNum);
            otherStructStat.put(i, wordsNum - easyStructWordsNum);

        }
    }

    private String getSimpleStructure(int length) {
        String simpleStruct = "а";
        char index = 'б';
        for(int i = 0; i < length; i++) {
            simpleStruct += index;
            index++;
        }
        return simpleStruct;
    }

    private void saveStatistics(HashMap<Integer,Integer> stat, String path, String name)
    {
        WorkWithFile file = new WorkWithFile(path + "\\" + name + ".txt");
        Set<Integer> keys = stat.keySet();
        for (int key : keys) {
            try {
                file.writeLineInTheEnd(Integer.toString(key) + "\t" + Integer.toString(stat.get(key)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAllStatistics(String path) {
        saveStatistics(wordStat, path, "wordStat");
        saveStatistics(structStat, path, "structStat");
        saveStatistics(easyStructStat, path, "easyStructStat");
        saveStatistics(otherStructStat, path, "otherStructStat");


    }
}

package com.nstu.substitutioncipher;


import com.nstu.substitutioncipher.experiment.Experiment;

import java.io.*;

public class source {
    private static final String inFilePath = "Experiments\\in\\input.txt";

    public static void main(String[] args) throws IOException {
        new Vocabulary("D:\\учеба\\Магистратура\\Диплом\\test\\MiddleVocabulary").addWordsFromTextFiles("D:\\учеба\\Магистратура\\Диплом\\test\\MiddleVocabularyTexts");
    }


    private static void MakeAllReadableExperiments(String inFile) throws IOException {
        for (String vocabulary : Vocabularies.vocabularies.keySet()
             ) {
            for(int i = 1; i < 3; i++) {
                new Experiment(vocabulary, i, inFile).MakeExperiment();
            }
        }
    }

    private static void MakeAllStatisticsExperiments(String inFile) throws IOException {
        for (String vocabulary : Vocabularies.vocabularies.keySet()
                ) {
            for(int i = 1; i < 3; i++) {
                new Experiment(vocabulary, i, inFile).MakeExperimentForStatistics();
            }
        }
    }
}
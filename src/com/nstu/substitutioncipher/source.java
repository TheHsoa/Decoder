package com.nstu.substitutioncipher;


import com.nstu.substitutioncipher.experiment.Experiment;
import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.vocabularies.decryptionvocabulary.Vocabularies;
import com.nstu.substitutioncipher.vocabularies.decryptionvocabulary.Vocabulary;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.*;

public class source {
    private static final String inFilePath = "Experiments\\in\\input.txt";

    public static void main(String[] args) throws IOException {
/*        for(int i = 1; i <=2; i++) {
            new Experiment("MiddleVocabulary", i, inFilePath).MakeExperiment();
            new Experiment("MiddleVocabulary", i, inFilePath).MakeExperimentForStatistics();
        }*/

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\учеба\\Магистратура\\Диплом\\test\\sets1.txt")));

        String text = TextStandardize.convertToStandardText(
        new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("D:\\учеба\\Магистратура\\Диплом\\test\\input.txt"))).readLine());

        SetOfWords setOfWords = new SetOfWords(text);

        for (WordBase word: setOfWords.getSetOfAbcWords()) {
            writer.write(word.getName() + System.getProperty("line.separator"));
        }

        writer.write("------------------------------" + System.getProperty("line.separator"));
        for(String vocabulary : Vocabularies.vocabularies.keySet())
            for(int i = 1; i < 3; i++) {
                setOfWords = new SetOfWords(text, new Vocabulary(Vocabularies.vocabularies.get(vocabulary)), i);
                for (WordBase word: setOfWords.getSetOfAbcWords()) {
                    writer.write(word.getName() + System.getProperty("line.separator"));
                }

                writer.write("------------------------------" + System.getProperty("line.separator"));
            }
            writer.close();



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
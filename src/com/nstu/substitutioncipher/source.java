package com.nstu.substitutioncipher;

import com.nstu.substitutioncipher.decryption.*;
import com.nstu.substitutioncipher.setofwords.SetOfWords;

import java.io.*;
import java.util.*;

import static javax.swing.UIManager.put;

public class source {
    public static void main(String[] args) throws IOException {
        String in = "D:\\учеба\\Магистратура\\Диплом\\test\\input.txt";
        String out = "D:\\учеба\\Магистратура\\Диплом\\test\\output.txt";
        String textPath = "D:\\учеба\\Магистратура\\Диплом\\test\\TestText.txt";
        String decryptTextPath = "D:\\учеба\\Магистратура\\Диплом\\test\\TestDecryptText.txt";
        String textDirPath = "D:\\учеба\\Магистратура\\Диплом\\test\\";
        String vocabularyPath = "D:\\учеба\\Магистратура\\Диплом\\test\\TestVocabulary";
        String bigVocabulary ="D:\\учеба\\Магистратура\\Диплом\\test\\LemmasAndTextWordsVocabulary";
        String lemmasVocabulary ="D:\\учеба\\Магистратура\\Диплом\\test\\LemmasVocabulary";
        String nonStandardText = "D:\\учеба\\Магистратура\\Диплом\\test\\NonStandardText.txt";
        String standardText = "D:\\учеба\\Магистратура\\Диплом\\test\\StandardText.txt";
       // TextStandardize.convertTextFileToStandardTextFile(new File(nonStandardText), new File(standardText), "Cp1251");
        //WordsCrossingMap wordsCrossingMap = new WordsCrossingMap((new SetOfWords("ааааб бвввв вггггг абвг")).getSetOfWords());
       // wordsCrossingMap.getWordsCrossingMap();
/*        Vocabulary vocabulary = new Vocabulary(bigVocabulary);
      //  vocabulary.addWordsFromStandardTextsFile(in);
      //  WordsDecrypt wordsDecrypt = new WordsDecrypt(new SetOfWords("атак обод аналитическая синусоиды вводимой вводимое").getSetOfAbcWords(), vocabulary);

    *//*
    Decrypt decrypt = new Decrypt();
        long date = System.currentTimeMillis();
        decrypt.DecryptFromFileToFile(new File(textPath), new File(decryptTextPath), vocabulary);
        System.out.println("time: " + (double)(System.currentTimeMillis() - date)/1000);
        System.out.println("iteration: " + decrypt.iterations);
        *//*

        DecryptionForm decryptionForm = new DecryptionForm(1);
        decryptionForm.DecryptFromStandardTextsFileToDecryptionFormFile(new File(in), new File(out), vocabulary);
*//*        DecryptionFormForStats decryptionFormForStats = new DecryptionFormForStats();
        decryptionFormForStats.DecryptFromStandardTextsFileToDecryptionFormFile(new File(in), new File(out), vocabulary);*/

        MakeExperiments(true);
    }

    private static void MakeExperiments(boolean forStats) throws IOException {
        final List<Experiment> experiments = GetExperiments();
        for (Experiment experiment:
             experiments) {
            if(!forStats) MakeExperiment(experiment);
            else MakeExperimentForStat(experiment);
        }


    }

    private static void MakeExperiment(Experiment experiment) throws IOException {
        Vocabulary vocabulary = new Vocabulary(experiment.getVocabulary());
        DecryptionForm decryptionForm = new DecryptionForm(experiment.getNumOfWordsOnChar());
        decryptionForm.DecryptFromStandardTextsFileToDecryptionFormFile(new File(experiment.getInFile()), new File(experiment.getOutFile()), vocabulary);
    }

    private static void MakeExperimentForStat(Experiment experiment) throws IOException {
        Vocabulary vocabulary = new Vocabulary(experiment.getVocabulary());
        DecryptionFormForStats decryptionFormForStats = new DecryptionFormForStats(experiment.getNumOfWordsOnChar());
        decryptionFormForStats.DecryptFromStandardTextsFileToDecryptionFormFile(new File(experiment.getInFile()), new File(experiment.getOutFile()), vocabulary);
    }

    private static List<Experiment> GetExperiments() {
        String inFile = "D:\\учеба\\Магистратура\\Диплом\\test\\input.txt";
        return new LinkedList<Experiment>() {{
                add(new Experiment("D:\\учеба\\Магистратура\\Диплом\\test\\LemmasAndTextWordsVocabulary",
                        1,
                        "D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\1chLemmasAndTextWordsVocabulary.txt",
                        inFile));
                add(new Experiment("D:\\учеба\\Магистратура\\Диплом\\test\\LemmasAndTextWordsVocabulary",
                        2,
                        "D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\2chLemmasAndTextWordsVocabulary.txt",
                        inFile));
                add(new Experiment("D:\\учеба\\Магистратура\\Диплом\\test\\TestVocabulary",
                        1,
                        "D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\1chTextWordsVocabulary.txt",
                        inFile));
                add(new Experiment("D:\\учеба\\Магистратура\\Диплом\\test\\TestVocabulary",
                        2,
                        "D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\2chTextWordsVocabulary.txt",
                        inFile));
                add(new Experiment("D:\\учеба\\Магистратура\\Диплом\\test\\LemmasVocabulary",
                        1,
                        "D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\1chLemmasVocabulary.txt",
                        inFile));
            }};
    }
}

class Experiment {
    String getVocabulary() {
        return vocabulary;
    }

    int getNumOfWordsOnChar() {
        return numOfWordsOnChar;
    }

    String getOutFile() {
        return outFile;
    }

    private String vocabulary;
    private int numOfWordsOnChar;
    private String outFile;

    String getInFile() {
        return inFile;
    }

    private String inFile;

    Experiment(String vocabulary, int numOfWordsOnChar, String outFile, String inFile) {
        this.vocabulary = vocabulary;
        this.numOfWordsOnChar = numOfWordsOnChar;
        this.outFile = outFile;
        this.inFile = inFile;
    }
}
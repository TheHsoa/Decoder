package com.nstu.substitutioncipher;


import com.nstu.substitutioncipher.decryption.DecryptionForm;
import com.nstu.substitutioncipher.experiment.Experiment;
import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.vocabularies.IVocabulary;
import com.nstu.substitutioncipher.vocabularies.decryptionvocabulary.Vocabularies;
import com.nstu.substitutioncipher.vocabularies.decryptionvocabulary.Vocabulary;
import com.nstu.substitutioncipher.vocabularies.kvocabulary.ReferenceVocabulary;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.*;

public class source {
    private static final String inFilePath = "Experiments\\in\\input.txt";

    public static void main(String[] args) throws IOException {
        IVocabulary vocabulary = new ReferenceVocabulary("KVocabularies\\dictionaryStr");
        DecryptionForm decryptionForm = new DecryptionForm(1);
        decryptionForm.DecryptFromStandardTextsFileToDecryptionFormFile(new File("D:\\учеба\\Магистратура\\Диплом\\test\\input.txt"), new File("D:\\учеба\\Магистратура\\Диплом\\test\\outputR.txt"), vocabulary);

/*        for(int i = 1; i <=2; i++) {
            new Experiment("MiddleVocabulary", i, inFilePath).MakeExperiment();
            new Experiment("MiddleVocabulary", i, inFilePath).MakeExperimentForStatistics();
       }*/
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
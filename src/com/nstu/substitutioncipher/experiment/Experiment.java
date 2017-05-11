package com.nstu.substitutioncipher.experiment;

import com.nstu.substitutioncipher.Vocabularies;
import com.nstu.substitutioncipher.Vocabulary;
import com.nstu.substitutioncipher.decryption.DecryptionForm;
import com.nstu.substitutioncipher.decryption.DecryptionFormForStats;

import java.io.File;
import java.io.IOException;

public class Experiment {

    private String vocabulary;
    private int numOfWordsOnChar;
    private String inFile;
    private final String outFilesPath = "Experiments\\out1\\";

    public Experiment(String vocabulary, int numOfWordsOnChar, String inFile) {
        this.vocabulary = vocabulary;
        this.numOfWordsOnChar = numOfWordsOnChar;
        this.inFile = inFile;
    }

    public String getVocabulary() {
        return Vocabularies.vocabularies.get(vocabulary);
    }

    public void MakeExperiment() throws IOException {
        Vocabulary vocabulary = new Vocabulary(getVocabulary());
        DecryptionForm decryptionForm = new DecryptionForm(numOfWordsOnChar);
        decryptionForm.DecryptFromStandardTextsFileToDecryptionFormFile(new File(inFile), new File(outFilesPath + "Readable\\" + numOfWordsOnChar + "ch" + this.vocabulary + ".txt"), vocabulary);
    }

    public void MakeExperimentForStatistics() throws IOException {
        Vocabulary vocabulary = new Vocabulary(getVocabulary());
        DecryptionFormForStats decryptionFormForStats = new DecryptionFormForStats(numOfWordsOnChar);
        decryptionFormForStats.DecryptFromStandardTextsFileToDecryptionFormFile(new File(inFile), new File(outFilesPath + "For Statistics\\" + numOfWordsOnChar + "ch" + this.vocabulary + ".txt"), vocabulary);
    }
}

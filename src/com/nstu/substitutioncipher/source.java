package com.nstu.substitutioncipher;

import com.nstu.substitutioncipher.decryption.Decrypt;
import com.nstu.substitutioncipher.decryption.DecryptionForm;
import com.nstu.substitutioncipher.decryption.WordsCrossingMap;
import com.nstu.substitutioncipher.decryption.WordsDecrypt;
import com.nstu.substitutioncipher.setofwords.SetOfWords;

import java.io.*;
import java.util.Date;

/**
 * Created by R_A_D on 08.11.2015.
 */
public class source {
    public static void main(String[] args) throws IOException {
        String in = "D:\\учеба\\Магистратура\\Диплом\\test\\input.txt";
        String out = "D:\\учеба\\Магистратура\\Диплом\\test\\output.txt";
        String textPath = "D:\\учеба\\Магистратура\\Диплом\\test\\TestText.txt";
        String decryptTextPath = "D:\\учеба\\Магистратура\\Диплом\\test\\TestDecryptText.txt";
        String textDirPath = "D:\\учеба\\Магистратура\\Диплом\\test\\";
        String vocabularyPath = "D:\\учеба\\Магистратура\\Диплом\\test\\TestVocabulary";
        String nonStandardText = "D:\\учеба\\Магистратура\\Диплом\\test\\NonStandardText.txt";
        String standardText = "D:\\учеба\\Магистратура\\Диплом\\test\\StandardText.txt";
       // TextStandardize.convertTextFileToStandardTextFile(new File(nonStandardText), new File(standardText), "Cp1251");
        //WordsCrossingMap wordsCrossingMap = new WordsCrossingMap((new SetOfWords("ааааб бвввв вггггг абвг")).getSetOfWords());
       // wordsCrossingMap.getWordsCrossingMap();
        Vocabulary vocabulary = new Vocabulary(vocabularyPath);
       // vocabulary.addWordsFromStandardTextsFile(in);
      //  WordsDecrypt wordsDecrypt = new WordsDecrypt(new SetOfWords("атак обод аналитическая синусоиды вводимой вводимое").getSetOfAbcWords(), vocabulary);

    /*
    Decrypt decrypt = new Decrypt();
        long date = System.currentTimeMillis();
        decrypt.DecryptFromFileToFile(new File(textPath), new File(decryptTextPath), vocabulary);
        System.out.println("time: " + (double)(System.currentTimeMillis() - date)/1000);
        System.out.println("iteration: " + decrypt.iterations);
        */
///*
        DecryptionForm decryptionForm = new DecryptionForm();
        decryptionForm.DecryptFromStandardTextsFileToDecryptionFormFile(new File(in), new File(out), vocabulary);

  //      */

    }
}
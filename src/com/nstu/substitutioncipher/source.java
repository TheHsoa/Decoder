package com.nstu.substitutioncipher;

import com.nstu.substitutioncipher.decryption.*;
import com.nstu.substitutioncipher.setofwords.SetOfWords;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
        Vocabulary vocabulary = new Vocabulary(bigVocabulary);
      //  vocabulary.addWordsFromStandardTextsFile(in);
      //  WordsDecrypt wordsDecrypt = new WordsDecrypt(new SetOfWords("атак обод аналитическая синусоиды вводимой вводимое").getSetOfAbcWords(), vocabulary);

    /*
    Decrypt decrypt = new Decrypt();
        long date = System.currentTimeMillis();
        decrypt.DecryptFromFileToFile(new File(textPath), new File(decryptTextPath), vocabulary);
        System.out.println("time: " + (double)(System.currentTimeMillis() - date)/1000);
        System.out.println("iteration: " + decrypt.iterations);
        */

        DecryptionForm decryptionForm = new DecryptionForm();
        decryptionForm.DecryptFromStandardTextsFileToDecryptionFormFile(new File(in), new File(out), vocabulary);
/*        DecryptionFormForStats decryptionFormForStats = new DecryptionFormForStats();
        decryptionFormForStats.DecryptFromStandardTextsFileToDecryptionFormFile(new File(in), new File(out), vocabulary);*/
    }

    public static void MakeExperiments() {
        Map<String, String> vocabularies = new HashMap<String,String>() {{
                    put("D:\\учеба\\Магистратура\\Диплом\\test\\LemmasAndTextWordsVocabulary", "D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\2chLemmasAndTextWordsVocabulary.txt");
                    put("D:\\учеба\\Магистратура\\Диплом\\test\\LemmasVocabulary","D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\2chLemmasVocabulary.txt");
                    put("D:\\учеба\\Магистратура\\Диплом\\test\\TestVocabulary","D:\\учеба\\Магистратура\\Диплом\\test\\опыты\\Для статистики\\новые\\2chTextWordsVocabulary.txt");
        }};

    }
}
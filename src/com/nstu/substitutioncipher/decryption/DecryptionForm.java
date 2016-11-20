package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.Vocabulary;

import java.io.*;
import java.util.*;

/**
 * Created by R_A_D on 30.10.2016.
 */
public class DecryptionForm {
    private static final String firstHeader = "_1_";
    private static final String standardFormAbc = "оеаи    нтс    врлп    кдм    йыь    уяюэ     згбчх    шжцщф";

    public void DecryptFromStandardTextsFileToDecryptionFormFile(File inFile, File outFile, Vocabulary vocabulary) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        String line;
        while(!(line = reader.readLine()).equals(firstHeader)) {
            //doing nothing
        }

        int headerIterator = 1;
        String currentHeader = firstHeader;

        do {
            if(line.equals(currentHeader)) {
                writer.write(line);

                headerIterator ++;
                currentHeader = "_" + headerIterator + "_";
            }
            else {
                if(!line.equals("")) {
                    line = delFirstAndLastSpaces(line);
                    int numOfWords = numOfWords(line);

                    writer.write(" N=" + (line.length() - numOfWords));

                    writer.write(" Nw=" + numOfWords);

                    SetOfWords words = new SetOfWords(line, vocabulary);

                    writer.write(" Ndw=" + words.getSetOfWords().size());

                    writer.write(" Nws=" + words.getSetOfAbcWords().size() + " ");

                    String decryptionStrings = DecryptionStrings(words, vocabulary);

                    writer.write(decryptionStrings);
                }
                else {
                    writer.write(line);
                    writer.newLine();
                }
            }

        } while((line = reader.readLine()) != null);
        reader.close();
        writer.close();

    }

    public static int numOfWords(String text)
    {
        return text.split(" ").length;
    }

    public static String delFirstAndLastSpaces(String text)
    {
        return text.trim();
    }

    private String DecryptionStrings(SetOfWords setOfWords, Vocabulary vocabulary) {
        Decrypt decrypt = new Decrypt();

        Map<Integer, Integer> abcMap = decrypt.DecipherAbc(setOfWords, vocabulary);
        if(abcMap == null) abcMap = new HashMap<>();

        String iterations = String.valueOf(decrypt.iterations);

        String decryptAbc = "";
        String isTrue = "";
        int numOfErrors = 0;
        int numOfCharsNotInMap = 0;

        for(int i = 0; i < standardFormAbc.length(); i++) {
            if(standardFormAbc.charAt(i) != ' ') {
                int c = standardFormAbc.charAt(i);
                if (abcMap.containsKey(c)) {
                    decryptAbc += (char) abcMap.get(c).intValue();

                    if(c != abcMap.get(c).intValue()) {
                        isTrue += "-";
                        numOfErrors ++;
                    }
                    else {
                        isTrue += " ";
                    }
                }
                else {
                    decryptAbc += "*";
                    isTrue += " ";
                    numOfCharsNotInMap ++;
                }
            }
            else {
                decryptAbc += " ";
                isTrue += " ";
            }
        }

        return "Ni=" + iterations + " Av=" + String.format("%.2f", decrypt.averageWordsInVocabulary) + " Ad=" + String.format("%.1f", decrypt.averageDept * 100) + "%" + System.getProperty("line.separator")
                + standardFormAbc.toUpperCase() + System.getProperty("line.separator")
                + decryptAbc.toUpperCase() + System.getProperty("line.separator")
                + (numOfErrors == 0 && numOfCharsNotInMap != 31 ? "" : (isTrue +  " ----" + (numOfCharsNotInMap == 31 ? numOfCharsNotInMap : numOfErrors)));
    }


}

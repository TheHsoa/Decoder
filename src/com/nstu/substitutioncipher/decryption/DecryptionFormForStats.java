package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.Vocabulary;
import com.nstu.substitutioncipher.setofwords.SetOfWords;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DecryptionFormForStats {
    private static final String firstHeader = "_1_";
    private static final String standardFormAbc = "оеаинтсврлпкдмйыьуяюэзгбчхшжцщф";
    private int numOfCharsInWords;

    public DecryptionFormForStats(int numOfCharsInWords) {
        this.numOfCharsInWords = numOfCharsInWords;
    }

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
                writer.write(line + System.lineSeparator());

                headerIterator ++;
                currentHeader = "_" + headerIterator + "_";
            }
            else {
                if(!line.equals("")) {

                    SetOfWords words = new SetOfWords(line.trim(), vocabulary, numOfCharsInWords);

                    writer.write(DecryptionStrings(words, vocabulary));
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

    private String DecryptionStrings(SetOfWords setOfWords, Vocabulary vocabulary) {
        Decrypt decrypt = new Decrypt(numOfCharsInWords);

        Map<Integer, Integer> abcMap = decrypt.DecipherAbc(setOfWords, vocabulary);

        String decryptAbc = "";

        for(int i = 0; i < standardFormAbc.length(); i++) {
                int c = standardFormAbc.charAt(i);
                if (abcMap.containsKey(c)) {
                    decryptAbc += (char) abcMap.get(c).intValue();
                }
                else {
                    decryptAbc += "-";
                }
        }
        decryptAbc += " 1\t0\t [VOC]";

        return decryptAbc.toUpperCase();
    }
}

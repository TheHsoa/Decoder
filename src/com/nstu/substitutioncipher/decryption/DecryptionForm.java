package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.SetOfWords;
import com.nstu.substitutioncipher.Vocabulary;

import java.io.*;
import java.util.*;

/**
 * Created by R_A_D on 30.10.2016.
 */
public class DecryptionForm {
    private static final String firstHeader = "_1_";

    public void DecryptFromStandardTextsFileToDecryptionFormFile(File inFile, File outFile, Vocabulary vocabulary) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        String line;
        while(!(line = reader.readLine()).equals(firstHeader)) {
            writer.write(line);
            writer.newLine();
        }

        int headerIterator = 1;
        String currentHeader = firstHeader;

        do {
            if(line.equals(currentHeader)) {
                writer.newLine();
                writer.write(line);
                writer.newLine();

                headerIterator ++;
                currentHeader = "_" + headerIterator + "_";
            }
            else {
                if(!line.equals("")) {
                    line = delFirstAndLastSpaces(line);
                    writer.write("N=" + numOfWords(line));
                    writer.newLine();

                    SetOfWords words = new SetOfWords(line);

                    writer.write("N_words=" + words.getSetOfWords().size());
                    writer.newLine();

                    writer.write("N_wordsInSet=" + words.getSetOfAbcWords().size());
                    writer.newLine();

                    String decryptionStrings = DecryptionStrings(words, vocabulary);

                    writer.write(decryptionStrings);
                    writer.newLine();
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

        Map<Integer, Integer> tempMap = decrypt.DecipherAbc(setOfWords, vocabulary);

        String iterations = String.valueOf(decrypt.iterations);

        if(tempMap == null) return iterations;

        SortedMap<Integer, Integer> abcMap  = new TreeMap<>();

        abcMap.putAll(tempMap);

        String abc = "";
        String decryptAbc = "";
        String isTrue = "";

        Iterator<Integer> keyIterator = abcMap.keySet().iterator();

        while(keyIterator.hasNext()) {
            int key = keyIterator.next();

            abc += (char)key;
            decryptAbc += (char)abcMap.get(key).intValue();
            isTrue += key == abcMap.get(key).intValue() ? " ": "-";
        }

        String spaces = " ";
        for(int i = iterations.length(); i > 0; i--) {
            spaces += " ";
        }

        return spaces + abc + System.getProperty("line.separator")
                + iterations + ":" + decryptAbc + System.getProperty("line.separator")
                + spaces + isTrue;
    }


}

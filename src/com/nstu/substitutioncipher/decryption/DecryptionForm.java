package com.nstu.substitutioncipher.decryption;

import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.Vocabulary;

import java.io.*;
import java.util.*;

public class DecryptionForm {
    private static final String firstHeader = "_1_";
    private static final String standardFormAbc = "оеаи    нтс    врлп    кдм    йыь    уяюэ     згбчх    шжцщф";
    private long totalOperationTime = 0;

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

        writer.write(System.getProperty("line.separator") + "TotalDecryptTime=" + totalOperationTime);

        writer.close();

    }

    private static int numOfWords(String text)
    {
        return text.split(" ").length;
    }

    private static String delFirstAndLastSpaces(String text)
    {
        return text.trim();
    }

    private String DecryptionStrings(SetOfWords setOfWords, Vocabulary vocabulary) {
        Decrypt decrypt = new Decrypt();

        Map<Integer, Integer> abcMap = decrypt.DecipherAbc(setOfWords, vocabulary);
        if(abcMap == null) abcMap = new HashMap<>();

        String decryptAbc = "";
        String isTrue = "";
        int numOfErrors = 0;
        int numOfCharsNotInMap = 0;

        totalOperationTime += decrypt.operationTime;

        for(int i = 0; i < standardFormAbc.length(); i++) {
            if(standardFormAbc.charAt(i) != ' ') {
                int c = standardFormAbc.charAt(i);
                if (abcMap.containsKey(c)) {
                    decryptAbc += (char) abcMap.get(c).intValue();

                    if(c != abcMap.get(c)) {
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

        return "Ni=" + decrypt.iterations + String.format(" Av=%.2f", decrypt.averageWordsInVocabulary) + String.format(" Ad=%.1f", decrypt.averageDepth * 100) + "%" + String.format(" T=%dms",decrypt.operationTime) +
                System.getProperty("line.separator")
                + standardFormAbc.toUpperCase() + System.getProperty("line.separator")
                + decryptAbc.toUpperCase() + System.getProperty("line.separator")
                + (numOfErrors == 0 && numOfCharsNotInMap != 31 ? "" : (isTrue +  " ----" + (numOfCharsNotInMap == 31 ? numOfCharsNotInMap : numOfErrors)));
    }


}

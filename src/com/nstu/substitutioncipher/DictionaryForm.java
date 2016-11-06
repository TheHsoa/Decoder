package com.nstu.substitutioncipher;

import java.io.*;
import java.util.Iterator;

/**
 * Created by R_A_D on 26.09.2016.
 */
public class DictionaryForm {

    private static final String firstHeader = "_1_";

    public static void saveStandardTextsToSetOfAbcWordsInDictionaryForm(String inFileName, String outFileName, String vocabularyPath) throws IOException {
        File inFile = new File(inFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

        File outFile = new File(outFileName);

        if(outFile.exists()) {
            outFile.delete();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));

        //Переписываем шапку
        String line;
        while(!(line = reader.readLine()).equals(firstHeader)) {
            writer.write(line);
            writer.newLine();
        }

        int headerIterator = 1;
        String currentHeader = firstHeader;

        Vocabulary vocabulary = new Vocabulary(vocabularyPath);
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

                    Iterator<Word> wordIterator = words.getSetOfAbcWords().iterator();

                    int currentLength = 0;
                    while(wordIterator.hasNext()) {
                        Word nextWord = wordIterator.next();
                        if (currentLength != nextWord.getLength() && currentLength != 0) {
                            writer.newLine();
                            writer.write(nextWord.getLength() + ": ");
                            writer.write(nextWord.getName().toUpperCase() + "(" +vocabulary.inVocabulary(nextWord)+ "); ");
                            currentLength = nextWord.getLength();
                        }
                        else {
                            if(currentLength == 0){
                                writer.write(nextWord.getLength() + ": ");
                                currentLength = nextWord.getLength();
                                writer.write(nextWord.getName().toUpperCase() + "(" +vocabulary.inVocabulary(nextWord)+ "); ");
                            } else {
                                writer.write(nextWord.getName().toUpperCase() + "(" +vocabulary.inVocabulary(nextWord)+ "); ");
                            }
                        }
                    }
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
}

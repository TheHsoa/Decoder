package com.nstu.substitutioncipher;

import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.vocabularies.IVocabulary;
import com.nstu.substitutioncipher.word.WordBase;

import java.io.*;
import java.util.Iterator;

public class DictionaryForm {

    private static final String firstHeader = "_1_";

    public static void saveStandardTextsToSetOfAbcWordsInDictionaryForm(String inFileName, String outFileName, IVocabulary vocabulary) throws IOException {
        File inFile = new File(inFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

        File outFile = new File(outFileName);

        if(outFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
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

                    Iterator<WordBase> wordIterator = words.getSetOfAbcWords().iterator();

                    int currentLength = 0;
                    while(wordIterator.hasNext()) {
                        WordBase nextWord = wordIterator.next();
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

    private static int numOfWords(String text)
    {
        return text.split(" ").length;
    }

    public static String delFirstAndLastSpaces(String text)
    {
        return text.trim();
    }
}

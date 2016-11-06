package com.nstu.substitutioncipher;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by R_A_D on 08.11.2015.
 */
public class Vocabulary {

    private String name;
    public Vocabulary(String name) {
        this.name = name;
    }

    public void easyAddNewWords(String fileName) throws IOException {
        WorkWithFile reader = new WorkWithFile(fileName);
        String line;
        Word word;
        while (((line = reader.readLine()) != "")) {
            word = new Word(line.substring(line.indexOf('"') + 1, line.indexOf('\t') - line.indexOf('"')));
            addNewWord(word);
        }
    }

    public boolean inVocabulary(Word word){
        try {
            WorkWithFile reader = new WorkWithFile(name + "\\" + word.getLength() + "\\" + word.getStructure() + ".txt");
            String line;
            while (((line = reader.readLine()) != "")) {
                if(word.getName().equals(line)) return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public List<String> getWordsInStructure(String structure) throws IOException {
        List<String> words = new ArrayList<>();

        WorkWithFile reader = new WorkWithFile(name + "\\" + structure.length() + "\\" + structure + ".txt");
        String line;

            while ((line = reader.readLine()) != "")
            {
                 words.add(line);
            }

        return words;
    }

    private void addNewWord(Word word) throws IOException {
        if(!inVocabulary(word)) {
            WorkWithFile dict = new WorkWithFile(name + "\\" + word.getLength() + "\\" + word.getStructure() + ".txt");
            dict.writeLineInTheEnd(word.getName());
        }
    }

    public void addWordsFromStandardTextFile(String path) {
        WorkWithFile reader = new WorkWithFile(path);
        String word;
        try {
            while((word = reader.readToNextSpace()) != "") {
                addNewWord(new Word(word));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWordsFromAllStandardTextFiles(String path) {
        File[] filesLen = new File(path).listFiles();
        for(int i = 0; i < filesLen.length; i++) {
            addWordsFromStandardTextFile(filesLen[i].getPath());
        }
    }

    public void addSetOfWords(SetOfWords setOfWords) throws IOException {
        Iterator<Word> wordIterator = setOfWords.getSetOfWords().iterator();
        while(wordIterator.hasNext()) {
            addNewWord(wordIterator.next());
        }
    }

    public void addWordsFromStandardText(String text) throws IOException {
        text = DictionaryForm.delFirstAndLastSpaces(text);
        SetOfWords setOfWords = new SetOfWords(text);
        addSetOfWords(setOfWords);
    }

    public void addWordsFromStandardTextsFile(String path) throws IOException {
        File inFile = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

        String text;
        int iterator = 1;
        while((text = reader.readLine()) != null) {
            if(text.equals("_" + iterator + "_")) {
                iterator ++;
                addWordsFromStandardText(reader.readLine());
            }
        }
        reader.close();
    }
}

package com.nstu.substitutioncipher.vocabularies.decryptionvocabulary;

import com.nstu.substitutioncipher.DictionaryForm;
import com.nstu.substitutioncipher.TextStandardize;
import com.nstu.substitutioncipher.WorkWithFile;
import com.nstu.substitutioncipher.setofwords.SetOfWords;
import com.nstu.substitutioncipher.vocabularies.IVocabulary;
import com.nstu.substitutioncipher.word.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Vocabulary implements IVocabulary {

    private String name;
    public Vocabulary(String name) {
        this.name = name;
    }

    public void easyAddNewWords(String fileName) throws IOException {
        WorkWithFile reader = new WorkWithFile(fileName);
        String line;
        Word word;
        while ((!Objects.equals(line = reader.readLine(), ""))) {
            word = new Word(line.substring(line.indexOf('"') + 1, line.indexOf('\t') - line.indexOf('"')));
            addNewWord(word);
        }
    }

    public boolean inVocabulary(WordBase word){
        try {
            WorkWithFile reader = new WorkWithFile(name + "\\" + word.getLength() + "\\" + word.getStructure() + ".txt");
            String line;
            while ((!Objects.equals(line = reader.readLine(), ""))) {
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

            while (!Objects.equals(line = reader.readLine(), ""))
            {
                 words.add(line);
            }

        return words;
    }

    public long getNumberOfWordsInStructure(String structure) throws IOException {
        File structureFile = new File(name + "\\" + structure.length() + "\\" + structure + ".txt");
        return !structureFile.exists() ? 0 : (structureFile.length() / (structure.length() + 2));
    }

    private void addNewWord(WordBase word) throws IOException {
        if(!inVocabulary(word)) {
            WorkWithFile dict = new WorkWithFile(name + "\\" + word.getLength() + "\\" + word.getStructure() + ".txt");
            dict.writeLineInTheEnd(word.getName());
        }
    }

    private void addWordsFromStandardTextFile(String path) {
        WorkWithFile reader = new WorkWithFile(path);
        String word;
        try {
            while(!Objects.equals(word = reader.readToNextSpace(), "")) {
                addNewWord(new Word(word));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWordsFromAllStandardTextFiles(String path) {
        File[] filesLen = new File(path).listFiles();

        if(filesLen == null) return;

        for (File aFilesLen : filesLen) {
            addWordsFromStandardTextFile(aFilesLen.getPath());
        }
    }

    private void addSetOfWords(SetOfWords setOfWords) throws IOException {
        for (WordBase wordBase : setOfWords.getSetOfWords()) {
            addNewWord(wordBase);
        }
    }

    private void addWordsFromStandardText(String text) throws IOException {
        text = DictionaryForm.delFirstAndLastSpaces(text);
        SetOfWords setOfWords = new SetOfWords(text);
        addSetOfWords(setOfWords);
    }

    private void addWordsFromTextFile(String path) throws IOException {
        File inFile = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

        String text;

        while((text = reader.readLine()) != null) {
            addWordsFromStandardText(TextStandardize.convertToStandardText(text));
        }
    }

    public void addWordsFromTextFiles(String path) throws IOException {
        File[] filesLen = new File(path).listFiles();

        if(filesLen == null) return;

        for (File aFilesLen : filesLen) {
            addWordsFromTextFile(aFilesLen.getPath());
        }
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

package com.nstu.substitutioncipher.vocabularies;

import com.nstu.substitutioncipher.word.WordBase;

import java.io.IOException;
import java.util.List;

public interface IVocabulary {
    public List<String> getWordsInStructure(String structure)  throws IOException;
    public long getNumberOfWordsInStructure(String structure)  throws IOException ;
    public boolean inVocabulary(WordBase word);
}

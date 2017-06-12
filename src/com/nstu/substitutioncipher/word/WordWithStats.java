package com.nstu.substitutioncipher.word;

import com.nstu.substitutioncipher.vocabularies.IVocabulary;

import java.io.IOException;

public class WordWithStats extends WordBase {
    private long structureStats;

    public long getStructureStats() {
        return structureStats;
    }

    public WordWithStats(String name, IVocabulary vocabulary) throws IOException {
        super(name);
        structureStats = vocabulary.getNumberOfWordsInStructure(structure);

    }

    @Override
    public int compareTo(Object o) {
        WordWithStats w = (WordWithStats) o;

        double firstStructureLen = (double) structureStats / length;
        double secondStructureLen = (double) w.structureStats / w.length;

        if(firstStructureLen == 0) {
            return 1;
        }

        if(secondStructureLen == 0) {
            return -1;
        }

        if(firstStructureLen == secondStructureLen) {
            if(structureStats == w.structureStats)
                return name.compareTo(w.getName());
            else
                return w.length > length ? 1 : -1;
        }

        return firstStructureLen > secondStructureLen ? 1 : -1;
    }
}

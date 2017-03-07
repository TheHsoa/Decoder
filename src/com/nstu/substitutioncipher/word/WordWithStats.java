package com.nstu.substitutioncipher.word;

import com.nstu.substitutioncipher.Vocabulary;

import java.io.IOException;

public class WordWithStats extends WordBase {
    private long structureStats;

    public long getStructureStats() {
        return structureStats;
    }

    public WordWithStats(String name, Vocabulary vocabulary) throws IOException {
        super(name);
        structureStats = vocabulary.getNumberOfWordsInStructure(structure);

    }

    @Override
    public int compareTo(Object o) {
        WordWithStats w = (WordWithStats) o;

        double firstStructureLen = (double) structureStats / length;
        double secondStructureLen = (double) w.structureStats / w.length;

        if(firstStructureLen == secondStructureLen && structureStats == w.structureStats) {
            return -1;
        }
        if(firstStructureLen == secondStructureLen) {
         //   return structureStats > w.structureStats ? 1 : -1;
            return w.length > length ? 1 : -1;
        }

        if(firstStructureLen == 0) {
            return -1;
        }

        return firstStructureLen > secondStructureLen ? 1 : -1;
    }
}

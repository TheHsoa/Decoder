package com.nstu.substitutioncipher.word;

import com.nstu.substitutioncipher.Vocabulary;

import java.io.IOException;
import java.util.Set;

/**
 * Created by R_A_D on 07.11.2016.
 */
public class WordWithStats extends WordBase {
    protected Vocabulary vocabulary;
    protected long structureStats;

    public WordWithStats(String name, Vocabulary vocabulary) throws IOException {
        super(name);
        this.vocabulary = vocabulary;
        structureStats = vocabulary.getNumberOfWordsInStructure(structure);

    }

    public long getStructureStats() {
        return structureStats;
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

        return firstStructureLen > secondStructureLen ? 1 : -1;
    }
}

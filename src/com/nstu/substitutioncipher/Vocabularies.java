package com.nstu.substitutioncipher;

import java.io.File;
import java.util.HashMap;

public class Vocabularies {
    public final static HashMap<String, String> vocabularies;
    static {
        final String path = "Vocabularies";
        vocabularies = new HashMap<>();
        File[] files = new File(path).listFiles();
        for (File file: files
             ) {
            vocabularies.put(file.getName(), file.getPath());
        }
    }
    
    public static void createStatisticForAllVocabularies() {
        for (String vocabulary: vocabularies.keySet()
             ) {
            new Statistic(vocabularies.get(vocabulary)).saveAllStatistics("Vocabularies statistic\\" + vocabulary + "Statistics");
        }
    }
}

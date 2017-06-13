package com.nstu.substitutioncipher.vocabularies.kvocabulary;

/**
 * Created by R_A_D on 13.06.2017.
 */
class KStructure {
    private String name;
    private long address;

    String getName() {
        return name;
    }

    long getAddress() {
        return address;
    }

    KStructure(String name, long address) {

        this.name = name;
        this.address = address;
    }
}

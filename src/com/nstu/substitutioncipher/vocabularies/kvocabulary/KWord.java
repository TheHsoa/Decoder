package com.nstu.substitutioncipher.vocabularies.kvocabulary;

class KWord {

    String getName() {
        return name;
    }

    long getAddress() {
        return address;
    }

    long getNextWordAddress() {
        return nextWordAddress;
    }

    KWord(String name, long address, long nextWordAddress) {

        this.name = name;
        this.address = address;
        this.nextWordAddress = nextWordAddress;
    }

    private String name;

    private long address;

    private long nextWordAddress;
}

package ru.skillbox.springlemmaexample.dto;

public class WordResponse {
    private final String word;
    private final String morphInfo;
    private final int count;

    public WordResponse(String word, String morphInfo, int count) {
        this.word = word;
        this.morphInfo = morphInfo;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public String getMorphInfo() {
        return morphInfo;
    }

    public int getCount() {
        return count;
    }
}

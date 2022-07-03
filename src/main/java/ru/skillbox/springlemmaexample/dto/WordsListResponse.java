package ru.skillbox.springlemmaexample.dto;

import java.util.List;

public class WordsListResponse {
    private final int count;
    private final List<WordResponse> words;

    public WordsListResponse(int count, List<WordResponse> words) {
        this.count = count;
        this.words = words;
    }

    public int getCount() {
        return count;
    }

    public List<WordResponse> getWords() {
        return words;
    }
}

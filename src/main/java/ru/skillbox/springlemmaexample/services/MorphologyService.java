package ru.skillbox.springlemmaexample.services;

import ru.skillbox.springlemmaexample.dto.SaveWordResponse;
import ru.skillbox.springlemmaexample.dto.SearchWordRequest;
import ru.skillbox.springlemmaexample.dto.WordsListResponse;

import java.util.List;

public interface MorphologyService {
    List<String> morphologyForms(String word);

    SaveWordResponse saveWord(String word);

    WordsListResponse searchWords(SearchWordRequest searchWordRequest);
}

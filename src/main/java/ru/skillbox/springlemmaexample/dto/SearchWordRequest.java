package ru.skillbox.springlemmaexample.dto;

import lombok.Data;

@Data
public class SearchWordRequest {
    private String query;
    private int limit;
}

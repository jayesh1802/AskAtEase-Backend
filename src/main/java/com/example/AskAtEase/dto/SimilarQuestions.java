package com.example.AskAtEase.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SimilarQuestions {
    private String query;
    private List<String> questions;
    private Map<String, List<String>> answers;
}

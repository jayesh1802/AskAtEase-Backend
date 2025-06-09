package com.example.AskAtEase.dto;

import lombok.Data;

import java.util.List;
@Data
public class SimilarQuestions {
    private String query;
    private List<String> questions;
}

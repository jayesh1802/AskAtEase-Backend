package com.example.AskAtEase.service;

import java.util.Map;

public interface SematicService {
    // for finding the similar question and answer based upon the request
    // it will map from String to Object
    public Map<String, Object> getSimilarQuestionsWithSummary(String query);
}

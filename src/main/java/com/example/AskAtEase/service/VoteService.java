package com.example.AskAtEase.service;

public interface VoteService {
    void upvoteQuestion(Long queId,String userId);
    void downvoteQuestion(Long queId,String userId);
    void upvoteAnswer(Long ansId,String userId);
    void downvoteAnswer(Long ansId,String userId);
}

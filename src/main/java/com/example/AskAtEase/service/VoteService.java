package com.example.AskAtEase.service;

public interface VoteService {

    // upvoting/downvoting of questions and answers..
    void upvoteQuestion(Long queId,String userId);
    void downvoteQuestion(Long queId,String userId);
    void upvoteAnswer(Long ansId,String userId);
    void downvoteAnswer(Long ansId,String userId);
}

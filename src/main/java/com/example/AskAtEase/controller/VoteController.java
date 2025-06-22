package com.example.AskAtEase.controller;

import com.example.AskAtEase.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    // Question Upvote
    @PostMapping("/question/{queId}/user/{userId}/upvote")
    public ResponseEntity<String> upvoteQuestion(@PathVariable Long queId,
                                                 @PathVariable String userId) {
        voteService.upvoteQuestion(queId, userId);
        return ResponseEntity.ok("Question upvoted successfully");
    }

    // Question Downvote
    @PostMapping("/question/{queId}/user/{userId}/downvote")
    public ResponseEntity<String> downvoteQuestion(@PathVariable Long queId,
                                                   @PathVariable String userId) {
        voteService.downvoteQuestion(queId, userId);
        return ResponseEntity.ok("Question downvoted successfully");
    }

    // Answer Upvote
    @PostMapping("/answer/{answerId}/user/{userId}/upvote")
    public ResponseEntity<String> upvoteAnswer(@PathVariable Long answerId,
                                               @PathVariable String userId) {
        voteService.upvoteAnswer(answerId, userId);
        return ResponseEntity.ok("Answer upvoted successfully");
    }

    // Answer Downvote
    @PostMapping("/answer/{answerId}/user/{userId}/downvote")
    public ResponseEntity<String> downvoteAnswer(@PathVariable Long answerId,
                                                 @PathVariable String userId) {
        voteService.downvoteAnswer(answerId, userId);
        return ResponseEntity.ok("Answer downvoted successfully");
    }
}

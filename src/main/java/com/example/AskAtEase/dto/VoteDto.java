package com.example.AskAtEase.dto;

import com.example.AskAtEase.enums.VoteType;
import lombok.Data;

@Data
public class VoteDto {
    private Long voteId;
    private VoteType vote;
    private String userId;
    private Long answerId;
    private Long queId;
}

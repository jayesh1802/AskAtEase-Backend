package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.VoteDto;
import com.example.AskAtEase.entity.Vote;
import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public Vote mapToVote(VoteDto voteDto) {
        Vote vote = new Vote();
        vote.setVoteId(voteDto.getVoteId());
        vote.setVote(voteDto.getVote());
        return vote;
    }


    public VoteDto mapToDto(Vote vote) {
        VoteDto dto = new VoteDto();
        dto.setVoteId(vote.getVoteId());
        dto.setVote(vote.getVote());
        dto.setUserId(vote.getUser().getUsername());

        if (vote.getQuestion() != null) {
            dto.setQueId(vote.getQuestion().getQueId());
        }

        if (vote.getAnswer() != null) {
            dto.setAnswerId(vote.getAnswer().getAnsId());
        }

        return dto;
    }
}

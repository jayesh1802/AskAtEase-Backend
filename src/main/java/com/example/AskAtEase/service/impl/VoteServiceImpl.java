package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.User;
import com.example.AskAtEase.entity.Vote;
import com.example.AskAtEase.enums.VoteType;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.repository.AnswerRepository;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.repository.VoteRepository;
import com.example.AskAtEase.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, QuestionRepository questionRepository,UserRepository userRepository,AnswerRepository answerRepository){
        this.voteRepository=voteRepository;
        this.questionRepository=questionRepository;
        this.userRepository=userRepository;
        this.answerRepository=answerRepository;
    }
    @Override
    public void upvoteQuestion(Long queId,String userId){
        Question question=questionRepository.findById(queId)
                .orElseThrow(()->new ResourceNotFound("question not found"));
        User user=userRepository.findByUsername(userId)
                .orElseThrow(()->new ResourceNotFound("User does not exist"));
        Optional<Vote> existingVoteOpt=voteRepository.findByUserAndQuestion(question,user);
        if(existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();
            if (existingVote.getVote() == VoteType.UPVOTE) {
                voteRepository.delete(existingVote);//toggle off
            } else if (existingVote.getVote() == VoteType.DOWNVOTE) {
                existingVote.setVote(VoteType.UPVOTE); // switch downvote->upvote
                voteRepository.save(existingVote);
            }
        }else{
            Vote newVote = new Vote();
            newVote.setVote(VoteType.UPVOTE);
            newVote.setUser(user);
            newVote.setQuestion(question);
            voteRepository.save(newVote);
        }
    }
    @Override
    public void downvoteQuestion(Long queId,String userId){
        Question question=questionRepository.findById(queId)
                .orElseThrow(()->new ResourceNotFound("question not found"));
        User user=userRepository.findByUsername(userId)
                .orElseThrow(()->new ResourceNotFound("User does not exist"));
        Optional<Vote> existingVoteOpt=voteRepository.findByUserAndQuestion(question,user);

        if(existingVoteOpt.isPresent()){
            Vote existingVote=existingVoteOpt.get();
            if(existingVote.getVote()==VoteType.DOWNVOTE) {
                voteRepository.delete(existingVote);
            }else if(existingVote.getVote()==VoteType.UPVOTE){
                existingVote.setVote(VoteType.DOWNVOTE);
                voteRepository.save(existingVote);
            }
        }
        else{
            Vote newVote= new Vote();
            newVote.setVote(VoteType.DOWNVOTE);
            newVote.setUser(user);
            newVote.setQuestion(question);
            voteRepository.save(newVote);
        }

    }
    @Override
    public void upvoteAnswer(Long answerId, String userId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFound("Answer not found"));

        User user = userRepository.findByUsername(userId)
                .orElseThrow(() -> new ResourceNotFound("User does not exist"));

        Optional<Vote> existingVoteOpt = voteRepository.findByUserAndAnswer(answer, user);

        if (existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();

            if (existingVote.getVote() == VoteType.UPVOTE) {
                voteRepository.delete(existingVote); // toggle off
            } else {
                existingVote.setVote(VoteType.UPVOTE); // switch from downvote to upvote
                voteRepository.save(existingVote);
            }
        } else {
            Vote newVote = new Vote();
            newVote.setVote(VoteType.UPVOTE);
            newVote.setUser(user);
            newVote.setAnswer(answer);
            voteRepository.save(newVote);
        }
    }
    @Override
    public void downvoteAnswer(Long answerId, String userId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFound("Answer not found"));

        User user = userRepository.findByUsername(userId)
                .orElseThrow(() -> new ResourceNotFound("User does not exist"));

        Optional<Vote> existingVoteOpt = voteRepository.findByUserAndAnswer(answer, user);

        if (existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();

            if (existingVote.getVote() == VoteType.DOWNVOTE) {
                voteRepository.delete(existingVote); // toggle off
            } else {
                existingVote.setVote(VoteType.DOWNVOTE); // switch from upvote to downvote
                voteRepository.save(existingVote);
            }
        } else {
            Vote newVote = new Vote();
            newVote.setVote(VoteType.DOWNVOTE);
            newVote.setUser(user);
            newVote.setAnswer(answer);
            voteRepository.save(newVote);
        }
    }
}

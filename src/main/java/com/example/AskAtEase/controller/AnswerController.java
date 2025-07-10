package com.example.AskAtEase.controller;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    // we are using to avoid tight coupling
    private final AnswerService answerService;
    public AnswerController(AnswerService answerService){
        this.answerService=answerService;
    }

    //add answer to question, queId and userId required..
    @PostMapping("/question/{queId}/user/{userId}")
    public ResponseEntity<AnswerDto> addAnswerToQuestion(
            @RequestBody AnswerDto answerDto,
            @PathVariable String userId,
            @PathVariable Long queId){
        AnswerDto savedAnswer=answerService.addAnswerToQuestion(answerDto,userId,queId);
        return new ResponseEntity<>(savedAnswer, HttpStatus.CREATED);
    }

    @GetMapping("/question/{queId}")
    public ResponseEntity<QuestionWithAnswerDto> getQuestionWithAnswers(
            @PathVariable Long queId
    ){
        QuestionWithAnswerDto queAns=answerService.getAllAnswersofQuestion(queId);
        return ResponseEntity.ok(queAns);

    }
}

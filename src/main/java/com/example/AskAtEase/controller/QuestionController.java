package com.example.AskAtEase.controller;

import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private QuestionService questionService;
    public QuestionController(QuestionService questionService){
        this.questionService=questionService;
    }
    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@RequestBody QuestionDto questionDto){
        QuestionDto savedQuestion=questionService.addQuestion(questionDto);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestions(){
        List<QuestionDto> questions=questionService.getAllQuestions();
        return ResponseEntity.ok(questions);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable("id") Long queId){
        questionService.deleteQuestion(queId);
        return ResponseEntity.ok("Deleted question Successfully");
    }
    @GetMapping("/answer")
    public ResponseEntity<?> getAllQuestionsWithAnswers(){
        List<QuestionWithAnswerDto> queAns=questionService.getAllQuestionAns();
        return ResponseEntity.ok(queAns);
    }

}

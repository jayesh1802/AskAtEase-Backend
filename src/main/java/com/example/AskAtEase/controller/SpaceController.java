package com.example.AskAtEase.controller;

import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.dto.SpaceDto;
import com.example.AskAtEase.entity.User;
import com.example.AskAtEase.service.SpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/space")
public class SpaceController {
    private SpaceService spaceService;
    public SpaceController(SpaceService spaceService){
        this.spaceService=spaceService;
    }

    @PostMapping
    public ResponseEntity<SpaceDto> createSpace(@RequestBody SpaceDto spaceDto){
        SpaceDto savedSpace=spaceService.createSpace(spaceDto);
        return  new ResponseEntity<>(savedSpace, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<SpaceDto>> getAllSpace(){
        List<SpaceDto> spaces=spaceService.getAllSpace();
        return ResponseEntity.ok(spaces);
    }
    @GetMapping("{id}")
    public  ResponseEntity<SpaceDto> getSpaceById(@PathVariable("id") Long spaceId){
        SpaceDto spaceDto= spaceService.getSpaceById(spaceId);
        return ResponseEntity.ok(spaceDto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSpaceById(@PathVariable("id") Long spaceId){
        spaceService.deleteSpaceById(spaceId);
        return ResponseEntity.ok("Deleted Space successfully");
    }
    @GetMapping("{spaceId}/queAns")
    public ResponseEntity<List<QuestionWithAnswerDto>> getQuestionsWithAnswersBySpaceId(@PathVariable("spacdId") Long spaceId){
        List<QuestionWithAnswerDto> queAns=spaceService.getQuestionsWithAnswersBySpaceId(spaceId);
        return ResponseEntity.ok(queAns);
    }

}

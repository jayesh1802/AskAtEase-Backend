package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {
}

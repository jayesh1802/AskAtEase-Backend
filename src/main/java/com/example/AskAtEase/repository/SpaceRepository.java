package com.example.AskAtEase.repository;

import com.example.AskAtEase.dto.SpaceDto;
import com.example.AskAtEase.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space,Long> {
}

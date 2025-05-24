package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}

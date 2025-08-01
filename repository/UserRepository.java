package com.example.placementprep.repository;

import com.example.placementprep.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (used for login)
    User findByEmail(String email);
    
    List<User> findByRole(String role);
}


package com.example.placementprep.repository;

import com.example.placementprep.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Find all quiz questions under a specific topic
    List<Quiz> findByTopicId(Long topicId);
}

package com.example.placementprep.repository;

import com.example.placementprep.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    // Get all quiz results by user ID
    List<QuizResult> findByUserId(Long userId);

    // Get quiz result for a specific topic and user
    QuizResult findByUserIdAndTopicId(Long userId, Long topicId);
}

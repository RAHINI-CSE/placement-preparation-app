package com.example.placementprep.service;

import com.example.placementprep.model.Quiz;
import com.example.placementprep.model.QuizResult;
import com.example.placementprep.repository.QuizRepository;
import com.example.placementprep.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    // Fetch quiz questions for a topic
    public List<Quiz> getQuizByTopicId(Long topicId) {
        return quizRepository.findByTopicId(topicId);
    }

    // Save user's quiz score
    public QuizResult saveQuizResult(QuizResult result) {
        return quizResultRepository.save(result);
    }

    // Get user's score for a topic (used to show result or prevent retake)
    public QuizResult getResultByUserAndTopic(Long userId, Long topicId) {
        return quizResultRepository.findByUserIdAndTopicId(userId, topicId);
    }

    // Get all quiz results of a student
    public List<QuizResult> getResultsByUserId(Long userId) {
        return quizResultRepository.findByUserId(userId);
    }
}

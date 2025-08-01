package com.example.placementprep.service;

import com.example.placementprep.model.Progress;
import com.example.placementprep.model.Topic;
import com.example.placementprep.model.User;
import com.example.placementprep.repository.ProgressRepository;
import com.example.placementprep.repository.TopicRepository;
import com.example.placementprep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    // Get all progress for a specific student
    public List<Progress> getProgressByUserId(Long userId) {
        return progressRepository.findByUserId(userId);
    }

    // Get progress of a specific topic for a student
    public Progress getProgressByUserAndTopic(Long userId, Long topicId) {
        return progressRepository.findByUserIdAndTopicId(userId, topicId);
    }

    // Save or update progress
    public Progress saveProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    // Mark a topic as completed
    public void markTopicCompleted(Long userId, Long topicId) {
        Progress existing = progressRepository.findByUserIdAndTopicId(userId, topicId);
        if (existing == null) {
            existing = new Progress();

            User user = userRepository.findById(userId).orElse(null);
            Topic topic = topicRepository.findById(topicId).orElse(null);

            if (user == null || topic == null) return;

            existing.setUser(user);
            existing.setTopic(topic);
        }
        existing.setStatus("Completed");
        progressRepository.save(existing);
    }
}

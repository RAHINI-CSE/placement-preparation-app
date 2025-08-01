package com.example.placementprep.service;

import com.example.placementprep.model.Topic;
import com.example.placementprep.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    // Get all topics
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    // Get topics by area of interest (e.g., "Cybersecurity", "Web Development")
    public List<Topic> getTopicsByArea(String area) {
        return topicRepository.findByArea(area);
    }

    // Get topic by ID
    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    // Create or update a topic
    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    // Delete topic
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}

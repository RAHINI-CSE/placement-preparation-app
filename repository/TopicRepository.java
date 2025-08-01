package com.example.placementprep.repository;

import com.example.placementprep.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    // Get all topics by interest area (e.g., "Cybersecurity", "Software Development")
    List<Topic> findByArea(String area);
}

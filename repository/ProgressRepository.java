package com.example.placementprep.repository;

import com.example.placementprep.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    // Get all progress entries by user ID
    List<Progress> findByUserId(Long userId);

    // Optional: Find progress for a specific topic of a user
    Progress findByUserIdAndTopicId(Long userId, Long topicId);
}

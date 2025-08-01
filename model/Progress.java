package com.example.placementprep.model;

import jakarta.persistence.*;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Reference to topic
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    // Status: e.g., "Completed", "In Progress", "Not Started"
    private String status;

    public Progress() {}

    public Progress(User user, Topic topic, String status) {
        this.user = user;
        this.topic = topic;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

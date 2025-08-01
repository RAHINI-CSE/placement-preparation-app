package com.example.placementprep.model;

import jakarta.persistence.*;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String area; // e.g., "Software Development", "Cybersecurity"

    private String description;

    private String resourceLink; // Can be a PDF link, YouTube video, article, etc.

    public Topic() {}

    public Topic(String title, String area, String description, String resourceLink) {
        this.title = title;
        this.area = area;
        this.description = description;
        this.resourceLink = resourceLink;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }
}

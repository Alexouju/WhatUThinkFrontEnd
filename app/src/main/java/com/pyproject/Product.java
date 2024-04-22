package com.pyproject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// Product.java
public class Product {
    private String name;
    private String description;
    private String aiDescription;
    private Specification specifications; // Specification object
    private List<Review> reviews;
    private List<String> pictures;

    public Product(String name, String description, String aiDescription, Specification specifications, List<Review> reviews, List<String> pictures) {
        this.name = name;
        this.description = description;
        this.aiDescription = aiDescription;
        this.specifications = specifications;
        this.reviews = reviews;
        this.pictures = pictures;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public Specification getSpecifications() { return specifications; }
    public void setSpecifications(Specification specifications) { this.specifications = specifications; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAiDescription() { return aiDescription; }
    public void setAiDescription(String aiDescription) { this.aiDescription = aiDescription; }


    public List<String> getPictures() { return pictures; }
    public void setPictures(List<String> pictures) { this.pictures = pictures; }
}


package com.example.quizbin1.data.model;

import java.util.UUID;

public class Subject {
    private UUID subjectId;
    private String subjectName;
    private String subjectDetail;
    private String urlImage;
    private String videoUrl;
    private UUID userId;

    public Subject() {
    }

    public Subject(UUID subjectId, String subjectName, String subjectDetail, String urlImage, String videoUrl, UUID userId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDetail = subjectDetail;
        this.urlImage = urlImage;
        this.videoUrl = videoUrl;
        this.userId = userId;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDetail() {
        return subjectDetail;
    }

    public void setSubjectDetail(String subjectDetail) {
        this.subjectDetail = subjectDetail;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}

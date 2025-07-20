package com.example.quizbin1.data.model.dto;

import java.util.Date;
import java.util.UUID;

public class InformationDTO {
    private UUID infoId;
    private UUID userId;
    private String fullName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String urlImage;

    public InformationDTO() {
    }

    public InformationDTO(UUID infoId, UUID userId, String fullName, String email, String phone, Date dateOfBirth, String urlImage) {
        this.infoId = infoId;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.urlImage = urlImage;
    }

    public UUID getInfoId() {
        return infoId;
    }

    public void setInfoId(UUID infoId) {
        this.infoId = infoId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}

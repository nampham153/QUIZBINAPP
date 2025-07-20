package com.example.quizbin1.data.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UpdateInformationRequestDTO {
    private UUID userId;
    private String fullName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String urlImage;

    public UpdateInformationRequestDTO(UUID userId, String fullName, String email, String phone, Date dateOfBirth, String urlImage) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        if (dateOfBirth != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.dateOfBirth = sdf.format(dateOfBirth);
        } else {
            this.dateOfBirth = null;
        }
        this.urlImage = urlImage;
    }

    // getter, setter...
}

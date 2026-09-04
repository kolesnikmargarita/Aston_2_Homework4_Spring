package by.kolesnik.aston_2_homework4.dto;

import lombok.Data;

@Data
public class SendEmailRequest {
    private String email;
    private String message;
}

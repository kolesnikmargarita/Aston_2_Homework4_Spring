package by.kolesnik.aston_2_homework4.dto;

import by.kolesnik.aston_2_homework4.constant.ValidationConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SendEmailRequest (

        @NotBlank(message = ValidationConstant.EMAIL_IS_BLANK_MESSAGE)
        @Pattern(regexp = ValidationConstant.EMAIL_REGEX, message = ValidationConstant.EMAIL_FORMAT_MESSAGE)
        String email,

        @NotBlank(message = ValidationConstant.MESSAGE_IS_BLANK_MESSAGE)
        String message
){}

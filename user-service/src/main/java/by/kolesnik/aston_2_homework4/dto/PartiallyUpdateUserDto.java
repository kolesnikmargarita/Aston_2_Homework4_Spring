package by.kolesnik.aston_2_homework4.dto;

import by.kolesnik.aston_2_homework4.constant.ValidationConstant;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record PartiallyUpdateUserDto (

    @Pattern(regexp = ValidationConstant.NAME_REGEX, message = ValidationConstant.NAME_FORMAT_MESSAGE)
    String name,

    @Pattern(regexp = ValidationConstant.EMAIL_REGEX, message = ValidationConstant.EMAIL_FORMAT_MESSAGE)
    String email,

    @Min(value = 0, message = ValidationConstant.AGE_IS_NEGATIVE_MESSAGE)
    @Max(value = 150, message = ValidationConstant.AGE_IS_EXCESSIVE_MESSAGE)
    Integer age
){}

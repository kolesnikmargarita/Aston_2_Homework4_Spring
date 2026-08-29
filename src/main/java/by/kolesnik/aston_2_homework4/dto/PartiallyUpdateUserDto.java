package by.kolesnik.aston_2_homework4.dto;

import by.kolesnik.aston_2_homework4.constant.ValidationConstant;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PartiallyUpdateUserDto {

    @Pattern(regexp = ValidationConstant.NAME_REGEX, message = ValidationConstant.NAME_FORMAT_MESSAGE)
    private String name;

    @Pattern(regexp = ValidationConstant.EMAIL_REGEX, message = ValidationConstant.EMAIL_FORMAT_MESSAGE)
    private String email;

    @Min(value = 0, message = ValidationConstant.AGE_IS_NEGATIVE_MESSAGE)
    @Max(value = 150, message = ValidationConstant.AGE_IS_EXCESSIVE_MESSAGE)
    private Integer age;
}

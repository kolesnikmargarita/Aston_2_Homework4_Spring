package by.kolesnik.aston_2_homework4.dto;

import by.kolesnik.aston_2_homework4.constant.ValidationConstant;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Builder
public record FullyUpdateUserDto (

    @NotBlank(message = ValidationConstant.NAME_IS_BLANK_MESSAGE)
    @Pattern(regexp = ValidationConstant.NAME_REGEX, message = ValidationConstant.NAME_FORMAT_MESSAGE)
    String name,

    @NotBlank(message = ValidationConstant.EMAIL_IS_BLANK_MESSAGE)
    @Pattern(regexp = ValidationConstant.EMAIL_REGEX, message = ValidationConstant.EMAIL_FORMAT_MESSAGE)
    String email,

    @NotNull(message = ValidationConstant.AGE_IS_BLANK_MESSAGE)
    @Min(value = 0, message = ValidationConstant.AGE_IS_NEGATIVE_MESSAGE)
    @Max(value = 150, message = ValidationConstant.AGE_IS_EXCESSIVE_MESSAGE)
    Integer age
) {}

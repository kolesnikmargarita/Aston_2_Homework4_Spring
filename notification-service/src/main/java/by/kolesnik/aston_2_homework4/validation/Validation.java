package by.kolesnik.aston_2_homework4.validation;

import by.kolesnik.aston_2_homework4.constant.ValidationConstant;
import by.kolesnik.aston_2_homework4.exception.InvalidNotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Validation {

    public void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            log.warn("Notification validation failed: Email is null or empty");
            throw new InvalidNotificationException(ValidationConstant.EMAIL_IS_BLANK_MESSAGE);
        }

        if (!email.matches(ValidationConstant.EMAIL_REGEX)) {
            log.warn("Notification validation failed: Email {} is invalid", email);
            throw new InvalidNotificationException(ValidationConstant.EMAIL_FORMAT_MESSAGE);
        }
    }
}

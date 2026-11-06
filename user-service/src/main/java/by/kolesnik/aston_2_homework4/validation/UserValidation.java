package by.kolesnik.aston_2_homework4.validation;


import by.kolesnik.aston_2_homework4.exception.DuplicateEmailException;
import by.kolesnik.aston_2_homework4.exception.IdIsNotValidException;
import by.kolesnik.aston_2_homework4.exception.IdIsNullException;
import by.kolesnik.aston_2_homework4.exception.UserNotFoundException;
import by.kolesnik.aston_2_homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public void validateId(Long id) {
        if (id == null) {
            log.warn("Validation failed: ID is null");
            throw new IdIsNullException("Id cannot be null");
        }
        if (id < 1) {
            log.warn("Validation failed: ID is {}, must be positive", id);
            throw new IdIsNotValidException("Id should be positive integer");
        }
    }

    // Проверка уникальности email
    public void validateEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already exists: " + email);
        }
    }

    // Проверка уникальности email при обновлении
    public void validateEmailUniqueForUpdate(String email, Long id) {
        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateEmailException("Email already exists: " + email);
        }
    }
}

package by.kolesnik.aston_2_homework4.facade;

import by.kolesnik.aston_2_homework4.publisher.UserEventPublisher;
import by.kolesnik.aston_2_homework4.dto.CreateUserDto;
import by.kolesnik.aston_2_homework4.dto.FullyUpdateUserDto;
import by.kolesnik.aston_2_homework4.dto.GetUserDto;
import by.kolesnik.aston_2_homework4.dto.PartiallyUpdateUserDto;
import by.kolesnik.aston_2_homework4.entity.User;
import by.kolesnik.aston_2_homework4.exception.IdIsNotNullException;
import by.kolesnik.aston_2_homework4.exception.UserNotFoundException;
import by.kolesnik.aston_2_homework4.mapper.UserMapper;
import by.kolesnik.aston_2_homework4.service.UserService;
import by.kolesnik.aston_2_homework4.validation.UserValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidation userValidation;
    private final UserEventPublisher eventPublisher;

    public GetUserDto findById(Long id) {
        log.info("Finding user: {}", id);

        userValidation.validateId(id);

        try {
            User user = userService.findById(id);
            log.info("User with id:{} is found", user.getId());
            return userMapper.toDto(user);
        } catch (EntityNotFoundException e) {
            log.warn("User with id:{} not found", id);
            throw new UserNotFoundException("It is incorrect id");
        }
    }

    public List<GetUserDto> findAll() {
        log.info("Finding all users");
        List<User> users = userService.findAll();

        log.info("List of all users is found");
        return users.stream().map(userMapper::toDto).toList();
    }

    public GetUserDto create(CreateUserDto dto) {
        log.info("Creating user");

        userValidation.validateEmailUnique(dto.email());

        User user = userMapper.toEntity(dto);
        if (user.getId() != null) {
            log.warn("Id should be null for creating. Current id is {}", user.getId());
            throw new IdIsNotNullException("ID must be null for create");
        }

        User created = userService.create(user);
        log.info("User created with id: {}", created.getId());

        eventPublisher.publishUserCreated(created);
        log.info("Message for created user with id:{} and email:{} was sent to Kafka",created.getId(), created.getEmail());

        return userMapper.toDto(created);
    }

    public GetUserDto updatePartially(Long id, PartiallyUpdateUserDto dto) {
        log.info("Partial updating user: {}", id);

        userValidation.validateId(id);
        if(dto.email() != null && !dto.email().isBlank()) {
            userValidation.validateEmailUniqueForUpdate(dto.email(), id);
        }

        try {
            User changeableUser = userService.findById(id);
            log.info("Partially changeable user found: {}", changeableUser.getId());
            User user = userMapper.toEntity(dto,changeableUser);
            User updatedUser = userService.update(user);
            log.info("User id : {} partially updated with name : {}, email: {}, age: {}",
                    updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getAge());
            return userMapper.toDto(updatedUser);
        } catch (EntityNotFoundException e) {
            log.warn("Partially changeable user with id:{} not found", id);
            throw new UserNotFoundException("It is incorrect id");
        }
    }

    public GetUserDto updateFully(Long id, FullyUpdateUserDto dto) {
        log.info("Full updating user: {}", id);

        userValidation.validateId(id);
        userValidation.validateEmailUniqueForUpdate(dto.email(), id);

        try {
            User changeableUser = userService.findById(id);
            log.info("Full changeable user found: {}", changeableUser.getId());
            User user = userMapper.toEntity(dto,changeableUser);
            User updatedUser = userService.update(user);
            log.info("User id : {} fully updated with name : {}, email: {}, age: {}",
                    updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getAge());

            return userMapper.toDto(updatedUser);
        } catch (EntityNotFoundException e) {
            log.warn("Full changeable user with id:{} not found", id);
            throw new UserNotFoundException("It is incorrect id");
        }
    }


    public void delete(Long id) {
        log.info("Deleting user: {}", id);

        userValidation.validateId(id);
        User removableUser = userService.findById(id);
        log.info("Removable user found: {}", removableUser.getId());

        userService.delete(id);
        log.info("User deleted: {}", id);

        eventPublisher.publishUserDeleted(removableUser);
        log.info("Message for deleted user with id:{} and email:{} was sand to Kafka", removableUser.getId(), removableUser.getEmail());
    }
}

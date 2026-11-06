package by.kolesnik.aston_2_homework4.service;

import by.kolesnik.aston_2_homework4.dto.*;
import by.kolesnik.aston_2_homework4.entity.User;
import by.kolesnik.aston_2_homework4.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        log.info("Finding user with id : {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '" + id + "' not found!"));

        log.info("User found: {}", user.getName());
        return user;
    }

    @Override
    public List<User> findAll() {
        log.info("Finding all users");
        List<User> users = userRepository.findAll();

        log.info("Method findAll find {} users", users.size());
        return users;
    }

    @Transactional
    @Override
    public User create(User user) {
        log.info("Creating user: {}", user.getName());

        User created = userRepository.save(user);
        log.info("User created with id: {}", created.getId());

        return created;
    }

    @Transactional
    @Override
    public User update(User user) {
        log.info("Updating user: {}", user.getId());

        User updatedUser = userRepository.save(user);
        log.info("User id : {} updated with name : {}, email: {}, age: {}",
                updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getAge());

        return user;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Deleting user: {}", id);
        userRepository.deleteById(id);
        log.info("User deleted: {}", id);
    }
}

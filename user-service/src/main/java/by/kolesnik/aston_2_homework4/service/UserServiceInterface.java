package by.kolesnik.aston_2_homework4.service;

import by.kolesnik.aston_2_homework4.entity.User;

import java.util.List;

public interface UserServiceInterface {

    User findById(Long id);
    List<User> findAll();
    User create(User user);
    User update(User user);
    void delete(Long id);
}

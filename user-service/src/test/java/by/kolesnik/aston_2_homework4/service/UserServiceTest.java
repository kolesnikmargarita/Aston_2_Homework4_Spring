package by.kolesnik.aston_2_homework4.service;

import by.kolesnik.aston_2_homework4.entity.User;
import by.kolesnik.aston_2_homework4.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_ShouldReturnSavedUser() {
        User userToSave = new User();
        userToSave.setName("Alice");
        userToSave.setEmail("alice@test.com");
        userToSave.setAge(25);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Alice");
        savedUser.setEmail("alice@test.com");
        savedUser.setAge(25);
        savedUser.setCreated_at(LocalDate.now());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.create(userToSave);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Alice", result.getName());
        assertEquals("alice@test.com", result.getEmail());
        assertEquals(25, result.getAge());
        assertNotNull(result.getCreated_at());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setName("Bob");
        user.setEmail("bob@test.com");
        user.setAge(30);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Bob", result.getName());

        verify(userRepository).findById(id);
    }

    @Test
    void findById_WhenUserNotFound_ShouldThrowException() {
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(id));

        verify(userRepository).findById(id);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Alice");
        user1.setEmail("alice@test.com");
        user1.setAge(25);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Bob");
        user2.setEmail("bob@test.com");
        user2.setAge(30);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());

        verify(userRepository).findAll();
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        Long id = 1L;

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName("Charles");
        updatedUser.setEmail("charles@test.com");
        updatedUser.setAge(28);

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(updatedUser);

        assertNotNull(result);
        assertEquals("Charles", result.getName());
        assertEquals(28, result.getAge());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        Long id = 1L;

        userService.delete(id);

        verify(userRepository).deleteById(id);
    }
}
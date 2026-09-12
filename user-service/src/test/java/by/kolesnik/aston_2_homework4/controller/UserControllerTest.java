package by.kolesnik.aston_2_homework4.controller;

import by.kolesnik.aston_2_homework4.dto.CreateUserDto;
import by.kolesnik.aston_2_homework4.dto.FullyUpdateUserDto;
import by.kolesnik.aston_2_homework4.dto.GetUserDto;
import by.kolesnik.aston_2_homework4.dto.PartiallyUpdateUserDto;
import by.kolesnik.aston_2_homework4.exception.DuplicateEmailException;
import by.kolesnik.aston_2_homework4.exception.UserNotFoundException;
import by.kolesnik.aston_2_homework4.facade.UserFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private UserFacade userFacade;

    private final String baseUrl = "/users";

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        CreateUserDto createDto =
                CreateUserDto.builder()
                        .name("John")
                        .email("john@test.com")
                        .age(25)
                        .build();

        GetUserDto responseDto =
                GetUserDto.builder()
                        .id(1L)
                        .name("John")
                        .email("john@test.com")
                        .age(25)
                        .created_at(LocalDate.now())
                        .build();

        when(userFacade.create(any(CreateUserDto.class))).thenReturn(responseDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void createUser_WithDuplicateEmail_ShouldReturnConflict() throws Exception {
        CreateUserDto createDto =
                CreateUserDto.builder()
                        .name("John")
                        .email("existing@test.com")
                        .age(25)
                        .build();

        when(userFacade.create(any(CreateUserDto.class)))
                .thenThrow(new DuplicateEmailException("Email already exists: existing@test.com"));

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already exists: existing@test.com"));
    }

    @Test
    void createUser_WithInvalidAge_ShouldReturnBadRequest() throws Exception {
        CreateUserDto createDto =
                CreateUserDto.builder()
                        .name("John")
                        .email("john@test.com")
                        .age(-5)
                        .build();

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void readAllUsers_ShouldReturnListOfUsers() throws Exception {
        GetUserDto user1 =
                GetUserDto.builder()
                        .id(1L)
                        .name("John")
                        .email("john@test.com")
                        .age(25)
                        .build();

        GetUserDto user2 =
                GetUserDto.builder()
                        .id(2L)
                        .name("Alice")
                        .email("alice@test.com")
                        .age(30)
                        .build();

        when(userFacade.findAll()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Alice"));
    }

    @Test
    void readUserById_WhenExists_ShouldReturnUser() throws Exception {
        GetUserDto responseDto =
                GetUserDto.builder()
                        .id(1L)
                        .name("John")
                        .email("john@test.com")
                        .age(25)
                        .created_at(LocalDate.now())
                        .build();

        when(userFacade.findById(1L)).thenReturn(responseDto);

        mockMvc.perform(get(baseUrl + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void readUserById_WhenNotFound_ShouldReturnNotFound() throws Exception {
        when(userFacade.findById(99L))
                .thenThrow(new UserNotFoundException("It is incorrect id"));

        mockMvc.perform(get(baseUrl + "/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("It is incorrect id"));
    }

    @Test
    void partialUpdateUser_WhenExists_ShouldReturnUpdatedUser() throws Exception {
        PartiallyUpdateUserDto updateDto =
                PartiallyUpdateUserDto.builder()
                        .name("John Updated")
                        .build();

        GetUserDto responseDto =
                GetUserDto.builder()
                        .id(1L)
                        .name("John Updated")
                        .email("john@test.com")
                        .age(25)
                        .created_at(LocalDate.now())
                        .build();

        when(userFacade.updatePartially(eq(1L), any(PartiallyUpdateUserDto.class))).thenReturn(responseDto);

        mockMvc.perform(patch(baseUrl + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john@test.com"));
    }

    @Test
    void fullUpdateUser_WhenExists_ShouldReturnUpdatedUser() throws Exception {
        FullyUpdateUserDto updateDto =
                FullyUpdateUserDto.builder()
                        .name("John Full")
                        .email("johnfull@test.com")
                        .age(30)
                        .build();

        GetUserDto responseDto =
                GetUserDto.builder()
                        .id(1L)
                        .email("johnfull@test.com")
                        .name("John Full")
                        .age(30)
                        .created_at(LocalDate.now())
                        .build();

        when(userFacade.updateFully(eq(1L), any(FullyUpdateUserDto.class))).thenReturn(responseDto);

        mockMvc.perform(put(baseUrl + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Full"))
                .andExpect(jsonPath("$.email").value("johnfull@test.com"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void deleteUser_WhenExists_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete(baseUrl + "/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_WhenNotFound_ShouldReturnNotFound() throws Exception {
        doThrow(new UserNotFoundException("It is incorrect id"))
                .when(userFacade).delete(99L);

        mockMvc.perform(delete(baseUrl + "/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("It is incorrect id"));
    }
}
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
        CreateUserDto createDto = new CreateUserDto();
        createDto.setName("John");
        createDto.setEmail("john@test.com");
        createDto.setAge(25);

        GetUserDto responseDto = new GetUserDto();
        responseDto.setId(1L);
        responseDto.setName("John");
        responseDto.setEmail("john@test.com");
        responseDto.setAge(25);
        responseDto.setCreated_at(LocalDate.now());

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
        CreateUserDto createDto = new CreateUserDto();
        createDto.setName("John");
        createDto.setEmail("existing@test.com");
        createDto.setAge(25);

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
        CreateUserDto createDto = new CreateUserDto();
        createDto.setName("John");
        createDto.setEmail("john@test.com");
        createDto.setAge(-5);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void readAllUsers_ShouldReturnListOfUsers() throws Exception {
        GetUserDto user1 = new GetUserDto();
        user1.setId(1L);
        user1.setName("John");
        user1.setEmail("john@test.com");
        user1.setAge(25);

        GetUserDto user2 = new GetUserDto();
        user2.setId(2L);
        user2.setName("Alice");
        user2.setEmail("alice@test.com");
        user2.setAge(30);

        when(userFacade.findAll()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Alice"));
    }

    @Test
    void readUserById_WhenExists_ShouldReturnUser() throws Exception {
        GetUserDto responseDto = new GetUserDto();
        responseDto.setId(1L);
        responseDto.setName("John");
        responseDto.setEmail("john@test.com");
        responseDto.setAge(25);
        responseDto.setCreated_at(LocalDate.now());

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
        PartiallyUpdateUserDto updateDto = new PartiallyUpdateUserDto();
        updateDto.setName("John Updated");

        GetUserDto responseDto = new GetUserDto();
        responseDto.setId(1L);
        responseDto.setName("John Updated");
        responseDto.setEmail("john@test.com");
        responseDto.setAge(25);
        responseDto.setCreated_at(LocalDate.now());

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
        FullyUpdateUserDto updateDto = new FullyUpdateUserDto();
        updateDto.setName("John Full");
        updateDto.setEmail("johnfull@test.com");
        updateDto.setAge(30);

        GetUserDto responseDto = new GetUserDto();
        responseDto.setId(1L);
        responseDto.setName("John Full");
        responseDto.setEmail("johnfull@test.com");
        responseDto.setAge(30);
        responseDto.setCreated_at(LocalDate.now());

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
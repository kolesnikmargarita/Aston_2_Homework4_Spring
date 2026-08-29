package by.kolesnik.aston_2_homework4.controller;

import by.kolesnik.aston_2_homework4.dto.*;
import by.kolesnik.aston_2_homework4.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping
    public GetUserDto create(@RequestBody @Valid CreateUserDto dto) {
        log.info("Creating user with name: {}", dto.getName());
        return userFacade.create(dto);
    }

    @GetMapping
    public List<GetUserDto> readAll() {
        log.info("Reading all users");
        return userFacade.findAll();
    }

    @GetMapping("/{id}")
    public GetUserDto readById(@PathVariable Long id) {
        log.info("Reading user with id: {}", id);
        return userFacade.findById(id);
    }

    @PatchMapping("/{id}")
    public GetUserDto updatePartially(@PathVariable Long id, @RequestBody @Valid PartiallyUpdateUserDto dto) {
        log.info("Partial updating user with id: {}", id);
        return userFacade.updatePartially(id, dto);
    }

    @PutMapping("/{id}")
    public GetUserDto updateFully(@PathVariable Long id, @RequestBody @Valid FullyUpdateUserDto dto) {
        log.info("Full updating user with id: {}", id);
        return userFacade.updateFully(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        userFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}

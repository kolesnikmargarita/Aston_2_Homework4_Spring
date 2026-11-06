package by.kolesnik.aston_2_homework4.controller;

import by.kolesnik.aston_2_homework4.assembler.UserModelAssembler;
import by.kolesnik.aston_2_homework4.controller.openapi.UserOpenApi;
import by.kolesnik.aston_2_homework4.dto.*;
import by.kolesnik.aston_2_homework4.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserOpenApi {

    private final UserFacade userFacade;
    private final UserModelAssembler userModelAssembler;

    @Override
    @PostMapping
    public UserModel create(@RequestBody @Valid CreateUserDto dto) {
        log.info("Creating user with name: {}", dto.name());
        return userModelAssembler.toModel(userFacade.create(dto));
    }

    @Override
    @GetMapping
    public CollectionModel<UserModel> readAll() {
        log.info("Reading all users");
        return userModelAssembler.toCollectionModel(userFacade.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public UserModel readById(@PathVariable Long id) {
        log.info("Reading user with id: {}", id);
        return userModelAssembler.toModel(userFacade.findById(id));
    }

    @Override
    @PatchMapping("/{id}")
    public UserModel updatePartially(@PathVariable Long id, @RequestBody @Valid PartiallyUpdateUserDto dto) {
        log.info("Partial updating user with id: {}", id);
        return userModelAssembler.toModel(userFacade.updatePartially(id, dto));
    }

    @Override
    @PutMapping("/{id}")
    public UserModel updateFully(@PathVariable Long id, @RequestBody @Valid FullyUpdateUserDto dto) {
        log.info("Full updating user with id: {}", id);
        return userModelAssembler.toModel(userFacade.updateFully(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        userFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}

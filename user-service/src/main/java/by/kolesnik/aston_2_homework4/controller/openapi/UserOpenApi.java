package by.kolesnik.aston_2_homework4.controller.openapi;

import by.kolesnik.aston_2_homework4.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User controller", description = "This controller allows performing CRUD operations on users")
public interface UserOpenApi {

    @Operation(
            method = "POST",
            summary = "Create a new user",
            description = "Add a new user in DB"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "The user has been successfully created",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject("""
                                    {
                                         "id": 29,
                                         "name": "John",
                                         "email": "john@list.ru",
                                         "age": 25,
                                         "created_at": "2026-09-11",
                                         "links": [
                                             {
                                               "rel": "self",
                                               "href": "http://localhost:8080/users/29"
                                             },
                                             {
                                               "rel": "users",
                                               "href": "http://localhost:8080/users"
                                             }
                                           ]
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "name: Only letters, spaces, and hyphens"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Conflict",
                    responseCode = "409",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "Email already exists: newEmail1@gmail.com"
                                     }
                                    """)
                    )
            )
    })
    UserModel create(@RequestBody @Valid CreateUserDto dto);

    @Operation(
            method = "GET",
            summary = "Read all users",
            description = "Get list of all users from DB"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "All users were found",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserModel.class)),
                            examples = @ExampleObject("""
                                    [
                                          {
                                              "id": 1,
                                              "name": "Alex",
                                              "email": "alex@gmail.com",
                                              "age": 24,
                                              "created_at": "2026-08-28",
                                              "links": [
                                                  {
                                                    "rel": "self",
                                                    "href": "http://localhost:8080/users/1"
                                                  },
                                                  {
                                                    "rel": "users",
                                                    "href": "http://localhost:8080/users"
                                                  }
                                                ]
                                          },
                                          {
                                              "id": 2,
                                              "name": "Alis",
                                              "email": "elis4@list.ru",
                                              "age": 26,
                                              "created_at": "2026-08-28",
                                              "links": [
                                                  {
                                                    "rel": "self",
                                                    "href": "http://localhost:8080/users/2"
                                                  },
                                                  {
                                                    "rel": "users",
                                                    "href": "http://localhost:8080/users"
                                                  }
                                                ]
                                          },
                                          {
                                              "id": 3,
                                              "name": "John",
                                              "email": "john@list.ru",
                                              "age": 25,
                                              "created_at": "2026-09-11",
                                              "links": [
                                                  {
                                                    "rel": "self",
                                                    "href": "http://localhost:8080/users/3"
                                                  },
                                                  {
                                                    "rel": "users",
                                                    "href": "http://localhost:8080/users"
                                                  }
                                                ]
                                          }
                                      ]
                                    """)
                    )
            )
    })
    CollectionModel<UserModel> readAll();

    @Operation(
            method = "GET",
            summary = "Read user by id",
            description = "Get user from DB by id in link"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "The user was successfully found",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject("""
                                    {
                                         "id": 29,
                                         "name": "John",
                                         "email": "john@list.ru",
                                         "age": 25,
                                         "created_at": "2026-09-11",
                                         "links": [
                                             {
                                               "rel": "self",
                                               "href": "http://localhost:8080/users/29"
                                             },
                                             {
                                               "rel": "users",
                                               "href": "http://localhost:8080/users"
                                             }
                                           ]
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Not found",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "It is incorrect id"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "Параметр 'id': Не удалось преобразовать значение 'ge' в требуемый тип 'Long'"
                                    }
                                    """)
                    )
            )
    })
    UserModel readById(@PathVariable Long id);

    @Operation(
            method = "PATCH",
            summary = "Partial user update",
            description = "Update some accessible fields for user from DB"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "The user has been successfully updated",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject("""
                                    {
                                         "id": 29,
                                         "name": "John",
                                         "email": "john@mail.ru",
                                         "age": 25,
                                         "created_at": "2026-09-11",
                                         "links": [
                                             {
                                               "rel": "self",
                                               "href": "http://localhost:8080/users/29"
                                             },
                                             {
                                               "rel": "users",
                                               "href": "http://localhost:8080/users"
                                             }
                                           ]
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Validation Error",
                                            summary = "Field validation exception",
                                            value = """
                                        {
                                             "message": "age: Age cannot be negative"
                                        }
                                        """
                                    ),
                                    @ExampleObject(
                                            name = "Type Mismatch Error",
                                            summary = "Invalid type ID in URL",
                                            value = """
                                        {
                                             "message": "Параметр 'id': Не удалось преобразовать значение 'ge' в требуемый тип 'Long'"
                                        }
                                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    description = "Conflict",
                    responseCode = "409",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "Email already exists: newEmail1@gmail.com"
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Not found",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "It is incorrect id"
                                    }
                                    """)
                    )
            )
    })
    UserModel updatePartially(@PathVariable Long id, @RequestBody @Valid PartiallyUpdateUserDto dto);

    @Operation(
            method = "PUT",
            summary = "Full update user",
            description = "Update all accessible fields for user from database"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "The user has been successfully updated",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject("""
                                    {
                                         "id": 29,
                                         "name": "Harry",
                                         "email": "harry@list.ru",
                                         "age": 27,
                                         "created_at": "2026-09-11",
                                         "links": [
                                             {
                                               "rel": "self",
                                               "href": "http://localhost:8080/users/29"
                                             },
                                             {
                                               "rel": "users",
                                               "href": "http://localhost:8080/users"
                                             }
                                           ]
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Validation Error",
                                            summary = "Field validation exception",
                                            value = """
                                        {
                                             "message": "age: Age cannot be negative"
                                        }
                                        """
                                    ),
                                    @ExampleObject(
                                            name = "Type Mismatch Error",
                                            summary = "Invalid type ID in URL",
                                            value = """
                                        {
                                             "message": "Параметр 'id': Не удалось преобразовать значение 'ge' в требуемый тип 'Long'"
                                        }
                                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    description = "Conflict",
                    responseCode = "409",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "Email already exists: newEmail1@gmail.com"
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Not found",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "It is incorrect id"
                                    }
                                    """)
                    )
            )
    })
    UserModel updateFully(@PathVariable Long id, @RequestBody @Valid FullyUpdateUserDto dto);

    @Operation(
            method = "DELETE",
            summary = "Delete user",
            description = "Delete user by id from link"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "The user has been successfully deleted",
                    responseCode = "204"
            ),
            @ApiResponse(
                    description = "Not found",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "User with id '4' not found!"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Conflict",
                    responseCode = "409",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "Email already exists: newEmail1@gmail.com"
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "Параметр 'id': Не удалось преобразовать значение 'ge' в требуемый тип 'Long'"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<Void> delete(@PathVariable Long id);
}

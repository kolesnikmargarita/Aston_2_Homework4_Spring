package by.kolesnik.aston_2_homework4.controller.openapi;

import by.kolesnik.aston_2_homework4.dto.ErrorResponse;
import by.kolesnik.aston_2_homework4.dto.SendEmailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Notification controller", description = "This controller allows sending notifications from Kafka to the user's email")
public interface NotificationOpenApi {

    @Operation(
            method = "POST",
            summary = "Send an email notification",
            description = "Sends an email message to the specified recipient address"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "The email message has been successfully sent",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                         "message": "Email is required"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<Void> sendEmail(@RequestBody @Valid SendEmailRequest request);
}
package by.kolesnik.aston_2_homework4.controller;

import by.kolesnik.aston_2_homework4.dto.SendEmailRequest;
import by.kolesnik.aston_2_homework4.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid SendEmailRequest request) {
        log.info("Received request to send email to: {}", request.email());
        notificationService.sendEmail(request.email(), request.message());
        return ResponseEntity.ok().build();
    }
}

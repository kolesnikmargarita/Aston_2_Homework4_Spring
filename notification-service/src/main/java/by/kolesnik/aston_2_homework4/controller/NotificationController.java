package by.kolesnik.aston_2_homework4.controller;

import by.kolesnik.aston_2_homework4.dto.SendEmailRequest;
import by.kolesnik.aston_2_homework4.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody SendEmailRequest request) {
        log.info("Received request to send email to: {}", request.getEmail());
        notificationService.sendEmail(request.getEmail(), request.getMessage());
        return ResponseEntity.ok().build();
    }
}

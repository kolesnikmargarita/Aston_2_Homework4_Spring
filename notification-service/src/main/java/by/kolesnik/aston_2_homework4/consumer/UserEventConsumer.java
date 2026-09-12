package by.kolesnik.aston_2_homework4.consumer;

import by.kolesnik.aston_2_homework4.enums.Operations;
import by.kolesnik.aston_2_homework4.dto.UserEvent;
import by.kolesnik.aston_2_homework4.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "users.events",
            groupId = "notification-service"
    )
    public void consumer(UserEvent event) {
        log.info("Received event: {}", event);
        notificationService.sendEmail(event.email(), event.operation().getMessage());
    }
}
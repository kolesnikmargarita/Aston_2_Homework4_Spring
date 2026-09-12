package by.kolesnik.aston_2_homework4;

import by.kolesnik.aston_2_homework4.enums.Operations;
import by.kolesnik.aston_2_homework4.service.NotificationService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NotificationIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        // Настраиваем базовый URL для обращения к API Mailpit
        RestAssured.baseURI = "http://" + mailpit.getHost();
        RestAssured.port = mailpit.getMappedPort(8025);

        // Очищаем почтовый ящик Mailpit перед каждым тестом
        given().delete("/api/v1/messages").then().statusCode(200);
    }

    @Test
    void shouldSendWelcomeEmailSuccessfully() {
        // 1. Действие: Вызываем бизнес-логику отправки письма
        String recipient = "user@example.com";
        notificationService.sendEmail(recipient, Operations.CREATE.getMessage());

        // 2. Проверка: Запрашиваем у Mailpit список полученных писем через его API
        given()
                .when()
                .get("/api/v1/messages")
                .then()
                .statusCode(200)
                .body("total", equalTo(1)) // Проверяем, что пришло ровно 1 письмо
                .body("messages[0].To[0].Address", equalTo(recipient))
                .body("messages[0].Snippet", containsString(Operations.CREATE.getMessage()));
    }
}

/*
* package by.kolesnik.aston_2_homework4;

import by.kolesnik.aston_2_homework4.dto.UserEvent;
import by.kolesnik.aston_2_homework4.enums.Operations;
import io.restassured.RestAssured;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;

public class NotificationIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate; // <-- Внедряем шаблон для отправки в Kafka

    @BeforeEach
    void setUp() {
        // Настраиваем базовый URL для обращения к API Mailpit
        RestAssured.baseURI = "http://" + mailpit.getHost();
        RestAssured.port = mailpit.getMappedPort(8025);

        // Очищаем почтовый ящик Mailpit перед каждым тестом
        given().delete("/api/v1/messages").then().statusCode(200);
    }

    @Bean
    public NewTopic usersEventsTopic() {
        return TopicBuilder.name("users.events")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Test
    void shouldProcessKafkaEventAndSendEmailSuccessfully() {
        // 1. Подготовка данных
        String recipient = "kafka-user@example.com";
        UserEvent event = new UserEvent(Operations.CREATE, recipient);

        // 2. Действие: Отправляем событие в топик Kafka вместо прямого вызова сервиса
        kafkaTemplate.send("users.events", event);

        // 3. Проверка: Так как Kafka работает асинхронно, мы ждем (макс. 5 секунд),
        // пока консьюмер прочитает сообщение и Mailpit зафиксирует отправку.
        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    given()
                            .when()
                            .get("/api/v1/messages")
                            .then()
                            .statusCode(200)
                            .body("total", equalTo(1)) // Проверяем, что письмо дошло
                            .body("messages[0].To[0].Address", equalTo(recipient))
                            .body("messages[0].Snippet", containsString(Operations.CREATE.getMessage()));
                });
    }
}
*/
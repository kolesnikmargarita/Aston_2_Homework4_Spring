package by.kolesnik.aston_2_homework4;

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
        notificationService.sendEmail(recipient, "Здравствуйте! Ваш аккаунт на сайте http://localhost:8080/users был успешно создан.");

        // 2. Проверка: Запрашиваем у Mailpit список полученных писем через его API
        given()
                .when()
                .get("/api/v1/messages")
                .then()
                .statusCode(200)
                .body("total", equalTo(1)) // Проверяем, что пришло ровно 1 письмо
                .body("messages[0].To[0].Address", equalTo(recipient))
                .body("messages[0].Snippet", containsString("Здравствуйте! Ваш аккаунт на сайте http://localhost:8080/users был успешно создан."));
    }
}

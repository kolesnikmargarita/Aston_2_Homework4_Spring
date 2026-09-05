package by.kolesnik.aston_2_homework4;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class BaseIntegrationTest {

    // Запускаем Mailpit. У него порт 1025 — SMTP, а 8025 — HTTP API для проверки писем
    @Container
    @SuppressWarnings("resource")
    static GenericContainer<?> mailpit = new GenericContainer<>("axllent/mailpit:v1.15")
            .withExposedPorts(1025, 8025);

    // Динамически меняем настройки Spring, чтобы он отправлял почту в наш контейнер
    @org.springframework.test.context.DynamicPropertySource
    static void configureMail(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", mailpit::getHost);
        registry.add("spring.mail.port", () -> mailpit.getMappedPort(1025));
    }
}

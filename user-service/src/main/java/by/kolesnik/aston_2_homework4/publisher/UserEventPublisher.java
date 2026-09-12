package by.kolesnik.aston_2_homework4.publisher;

import by.kolesnik.aston_2_homework4.dto.UserEvent;
import by.kolesnik.aston_2_homework4.entity.User;
import by.kolesnik.aston_2_homework4.enums.Operations;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UserEventPublisher {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    private final String topic;

    public UserEventPublisher(
            @Value("${app.kafka.topic}") String topic) {
        this.topic = topic;

        String bootstrapServers = "localhost:9092"; // ← по умолчанию

        String envBootstrap = System.getenv("SPRING_KAFKA_BOOTSTRAP_SERVERS");
        if (envBootstrap != null && !envBootstrap.isBlank()) {
            bootstrapServers = envBootstrap;
        }

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 1. Передаем класс сериализатора СТРОКОЙ — это убирает deprecation предупреждение
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");

        // 2. Добавляем важную настройку для корректной работы с JSON в многомодульных проектах
        // Отключаем передачу ID типов в заголовках, так как наш Consumer настроен на жесткий тип по умолчанию
        config.put("spring.json.add.type.headers", false);

        DefaultKafkaProducerFactory<String, UserEvent> factory = new DefaultKafkaProducerFactory<>(config);
        this.kafkaTemplate = new KafkaTemplate<>(factory);

        log.info("UserEventPublisher initialized with bootstrap.servers = {}", bootstrapServers);
    }

    public void publishUserCreated(User user) {
        publish(Operations.CREATE, user);
    }

    public void publishUserDeleted(User user) {
        publish(Operations.DELETE, user);
    }

    private void publish(Operations operation, User user) {
        UserEvent event = new UserEvent(operation, user.getEmail());
        kafkaTemplate.send(topic, user.getId().toString(), event);
        log.info("Published event about creating user with id:{} and email:{}", user.getId(), user.getEmail());
    }
}

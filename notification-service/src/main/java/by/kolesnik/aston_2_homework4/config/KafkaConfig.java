package by.kolesnik.aston_2_homework4.config;

import by.kolesnik.aston_2_homework4.dto.UserEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, UserEvent> consumerFactory() {
        // Берем базовые свойства, которые Спринг настроил автоматически (включая правильный bootstrap-servers)
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());

        if (!props.containsKey(ConsumerConfig.GROUP_ID_CONFIG)) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service");
        }

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonDeserializer");
        props.put("spring.json.value.default.type", "by.kolesnik.aston_2_homework4.dto.UserEvent");
        props.put("spring.json.trusted.packages", "*");
        props.put("spring.json.use.type.headers", false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

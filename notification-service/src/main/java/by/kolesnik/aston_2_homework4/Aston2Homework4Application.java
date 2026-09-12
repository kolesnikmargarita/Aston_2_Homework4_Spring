package by.kolesnik.aston_2_homework4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Aston2Homework4Application {

    public static void main(String[] args) {
        SpringApplication.run(Aston2Homework4Application.class, args);
    }

}

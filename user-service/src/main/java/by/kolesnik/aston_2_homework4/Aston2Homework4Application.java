package by.kolesnik.aston_2_homework4;

import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SpringDocHateoasConfiguration.class)
public class Aston2Homework4Application {

    public static void main(String[] args) {
        SpringApplication.run(Aston2Homework4Application.class, args);
    }

}

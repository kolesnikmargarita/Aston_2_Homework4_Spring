package by.kolesnik.aston_2_homework4.enums;

import lombok.Getter;

@Getter
public enum Operations {
    CREATE("Здравствуйте! Ваш аккаунт на сайте http://localhost:8080/users был успешно создан."),
    DELETE("Здравствуйте! Ваш аккаунт был удалён.");
    private final String message;

    Operations(String message) {
        this.message = message;
    }
}

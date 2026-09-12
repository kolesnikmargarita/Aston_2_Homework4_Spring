package by.kolesnik.aston_2_homework4.dto;

import by.kolesnik.aston_2_homework4.enums.Operations;

public record UserEvent(Operations operation, String email){}
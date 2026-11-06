package by.kolesnik.aston_2_homework4.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetUserDto (

    Long id,
    String name,
    String email,
    int age,
    LocalDate created_at
) {}

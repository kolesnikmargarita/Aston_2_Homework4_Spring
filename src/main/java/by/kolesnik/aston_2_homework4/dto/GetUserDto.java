package by.kolesnik.aston_2_homework4.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetUserDto {

    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDate created_at;
}

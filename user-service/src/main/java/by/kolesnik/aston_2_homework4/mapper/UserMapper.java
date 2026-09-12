package by.kolesnik.aston_2_homework4.mapper;

import by.kolesnik.aston_2_homework4.dto.CreateUserDto;
import by.kolesnik.aston_2_homework4.dto.FullyUpdateUserDto;
import by.kolesnik.aston_2_homework4.dto.GetUserDto;
import by.kolesnik.aston_2_homework4.dto.PartiallyUpdateUserDto;
import by.kolesnik.aston_2_homework4.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public GetUserDto toDto(User entity) {
        return GetUserDto.builder()
                        .id(entity.getId())
                        .created_at(entity.getCreated_at())
                        .email(entity.getEmail())
                        .name(entity.getName())
                        .age(entity.getAge())
                        .build();
    }

    public User toEntity(CreateUserDto dto) {
        User entity = new User();

        entity.setAge(dto.age());
        entity.setName(dto.name());
        entity.setEmail(dto.email());

        return entity;
    }

    public User toEntity(PartiallyUpdateUserDto dto, User user) {

        if(dto.age() != null) {
            user.setAge(dto.age());
        }
        if(dto.name() != null && !dto.name().isBlank()) {
            user.setName(dto.name());
        }
        if(dto.email() != null && !dto.email().isBlank()) {
            user.setEmail(dto.email());
        }

        return user;
    }

    public User toEntity(FullyUpdateUserDto dto, User user) {

        user.setAge(dto.age());
        user.setName(dto.name());
        user.setEmail(dto.email());

        return user;
    }
}

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
        GetUserDto dto = new GetUserDto();

        dto.setId(entity.getId());
        dto.setAge(entity.getAge());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setCreated_at(entity.getCreated_at());

        return dto;
    }

    public User toEntity(CreateUserDto dto) {
        User entity = new User();

        entity.setAge(dto.getAge());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

        return entity;
    }

    public User toEntity(PartiallyUpdateUserDto dto, User user) {

        if(dto.getAge() != null) {
            user.setAge(dto.getAge());
        }
        if(dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }
        if(dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }

        return user;
    }

    public User toEntity(FullyUpdateUserDto dto, User user) {

        user.setAge(dto.getAge());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return user;
    }
}

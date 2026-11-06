package by.kolesnik.aston_2_homework4.assembler;

import by.kolesnik.aston_2_homework4.controller.UserController;
import by.kolesnik.aston_2_homework4.dto.UserModel;
import by.kolesnik.aston_2_homework4.dto.GetUserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<GetUserDto, UserModel> {

    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(GetUserDto dto) {
        UserModel model = new UserModel();
        model.setId(dto.id());
        model.setName(dto.name());
        model.setEmail(dto.email());
        model.setAge(dto.age());
        model.setCreatedAt(dto.created_at());

        // Ссылка на самого себя (self)
        model.add(linkTo(methodOn(UserController.class).readById(dto.id())).withSelfRel());

        // Ссылка на всех пользователей
        model.add(linkTo(methodOn(UserController.class).readAll()).withRel("users"));

        return model;
    }
}
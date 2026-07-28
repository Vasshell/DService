package ru.vasshell.dservice.mapper;

import org.mapstruct.Mapper;
import ru.vasshell.dservice.dto.UserDto;
import ru.vasshell.dservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);
    User dtoToEntity(UserDto user);
}

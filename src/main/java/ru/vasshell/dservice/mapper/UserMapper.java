package ru.vasshell.dservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vasshell.dservice.dto.UserDto;
import ru.vasshell.dservice.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto user);

    @Mapping(target = "id", expression = "java(rs.getObject(\"id\", java.util.UUID.class))")
    @Mapping(target = "firstName", expression = "java(rs.getString(\"first_name\"))")
    @Mapping(target = "lastName", expression = "java(rs.getString(\"last_name\"))")
    @Mapping(target = "age", expression = "java(rs.getInt(\"age\"))")
    User toEntity(ResultSet rs) throws SQLException;
}

package vasshell.dservice.mapper;

import org.mapstruct.Mapper;
import vasshell.dservice.dto.UserDto;
import vasshell.dservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);
    User dtoToEntity(UserDto user);
}

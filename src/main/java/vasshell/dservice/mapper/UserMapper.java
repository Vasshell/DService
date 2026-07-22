package vasshell.dservice.mapper;

import org.mapstruct.Mapper;
import vasshell.dservice.dto.UserCreateDto;
import vasshell.dservice.dto.UserGetDto;
import vasshell.dservice.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserGetDto userToGetDto(User user);
    List<UserGetDto> userToGetDto(List<User> user);
    User createDtoToUser(UserCreateDto user);
}

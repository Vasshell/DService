package vasshell.dservice.user;

import org.mapstruct.Mapper;
import vasshell.dservice.user.dto.CreateUserDto;
import vasshell.dservice.user.dto.GetUserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    GetUserDto userToGetDto(User user);
    List<GetUserDto> userToGetDto(List<User> user);
    User createDtoToUser(CreateUserDto user);
}

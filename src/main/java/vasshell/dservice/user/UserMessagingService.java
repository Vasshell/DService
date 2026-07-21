package vasshell.dservice.user;

import vasshell.dservice.user.dto.CreateUserDto;
import vasshell.dservice.user.dto.UpdateUserDto;

import java.util.UUID;

public interface UserMessagingService {
    void deleteUserMessage(UUID id);

    void updateUserMessage(UpdateUserDto user);

    void createUserMessage(CreateUserDto user);
}

package vasshell.dservice.publisher;

import vasshell.dservice.dto.UserDto;

import java.util.UUID;

public interface UserSender {
    void deleteUserMessage(UUID id);

    void updateUserMessage(UserDto user);

    void createUserMessage(UserDto user);
}

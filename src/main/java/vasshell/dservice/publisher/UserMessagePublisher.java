package vasshell.dservice.publisher;

import vasshell.dservice.dto.UserCreateDto;
import vasshell.dservice.dto.UserUpdateDto;

import java.util.UUID;

public interface UserMessagePublisher {
    void deleteUserMessage(UUID id);

    void updateUserMessage(UserUpdateDto user);

    void createUserMessage(UserCreateDto user);
}

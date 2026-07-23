package vasshell.dservice.sender;

import vasshell.dservice.dto.UserDto;

import java.util.UUID;

public interface UserSender {

    void sendCreateMessage(UserDto user);

    void sendUpdateMessage(UserDto user);

    void sendDeleteMessage(UUID id);

}

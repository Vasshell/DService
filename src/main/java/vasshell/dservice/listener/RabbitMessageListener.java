package vasshell.dservice.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.config.RabbitConfig;
import vasshell.dservice.dto.UserCreateDto;
import vasshell.dservice.dto.UserUpdateDto;
import vasshell.dservice.service.UserService;

import java.util.UUID;

public class RabbitMessageListener {


    private final UserService userService;
    private final ObjectMapper objectMapper;

    public RabbitMessageListener(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_CREATE)
    public void receiveCreateMessage(Message message){
        userService.createUser(objectMapper.readValue(message.getBody(), UserCreateDto.class));
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_DELETE)
    public void receiveDeleteMessage(Message message){
        userService.deleteUser(objectMapper.readValue(message.getBody(), UUID.class));
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_UPDATE)
    public void receivePatchMessage(Message message){
        UserUpdateDto request = objectMapper.readValue(message.getBody(), UserUpdateDto.class);
        userService.updateUser(request);
    }
}

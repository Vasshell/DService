package vasshell.dservice.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.config.RabbitConfig;
import vasshell.dservice.user.User;
import vasshell.dservice.user.UserService;
import vasshell.dservice.user.dto.UpdateUserDto;

import java.util.UUID;

@Component
public class RabbitMessageReceiver {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public RabbitMessageReceiver(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_CREATE)
    public void receiveCreateMessage(Message message){
        userService.createUser(objectMapper.readValue(message.getBody(), User.class));
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_DELETE)
    public void receiveDeleteMessage(Message message){
        userService.deleteUser(objectMapper.readValue(message.getBody(), UUID.class));
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_UPDATE)
    public void receivePatchMessage(Message message){
        UpdateUserDto request = objectMapper.readValue(message.getBody(), UpdateUserDto.class);
        userService.updateUser(request);
    }
}

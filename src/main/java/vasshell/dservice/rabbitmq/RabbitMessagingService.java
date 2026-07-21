package vasshell.dservice.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.user.UserMessagingService;
import vasshell.dservice.user.UserService;
import vasshell.dservice.user.dto.CreateUserDto;
import vasshell.dservice.user.dto.UpdateUserDto;

import java.util.UUID;

@Service
public class RabbitMessagingService implements UserMessagingService {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;

    public RabbitMessagingService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, UserService userService) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.userService = userService;
    }

    @Override
    public void deleteUserMessage(UUID id) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_DELETE, objectMapper.writeValueAsBytes(id));
    }

    @Override
    public void createUserMessage(CreateUserDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_CREATE, objectMapper.writeValueAsBytes(user));
    }

    @Override
    public void updateUserMessage(UpdateUserDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_UPDATE, objectMapper.writeValueAsBytes(user));
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_CREATE)
    public void receiveCreateMessage(Message message){
        userService.createUser(objectMapper.readValue(message.getBody(), CreateUserDto.class));
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

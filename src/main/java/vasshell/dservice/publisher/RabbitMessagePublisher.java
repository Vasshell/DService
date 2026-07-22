package vasshell.dservice.publisher;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.config.RabbitConfig;
import vasshell.dservice.dto.UserCreateDto;
import vasshell.dservice.dto.UserUpdateDto;
import vasshell.dservice.service.UserService;

import java.util.UUID;

@Service
public class RabbitMessagePublisher implements UserMessagePublisher {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMessagePublisher(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, UserService userService) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void deleteUserMessage(UUID id) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_DELETE, objectMapper.writeValueAsBytes(id));
    }

    @Override
    public void createUserMessage(UserCreateDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_CREATE, objectMapper.writeValueAsBytes(user));
    }

    @Override
    public void updateUserMessage(UserUpdateDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_UPDATE, objectMapper.writeValueAsBytes(user));
    }
}

package vasshell.dservice.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.config.RabbitConfig;
import vasshell.dservice.user.UserMessagingService;
import vasshell.dservice.user.dto.CreateUserDto;
import vasshell.dservice.user.dto.UpdateUserDto;

import java.util.UUID;

@Service
public class RabbitMessagingService implements UserMessagingService {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMessagingService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
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
}

package vasshell.dservice.publisher;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.config.RabbitConfig;
import vasshell.dservice.dto.UserDto;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserSenderImpl implements UserSender {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    @Override

    public void deleteUserMessage(UUID id) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_DELETE, objectMapper.writeValueAsBytes(id));
    }

    @Override
    public void createUserMessage(UserDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_CREATE, objectMapper.writeValueAsBytes(user));
    }

    @Override
    public void updateUserMessage(UserDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_UPDATE, objectMapper.writeValueAsBytes(user));
    }
}

package ru.vasshell.dservice.sender;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import ru.vasshell.dservice.config.RabbitConfig;
import ru.vasshell.dservice.dto.UserDto;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RabbitUserSender implements UserSender {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendCreateMessage(UserDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_CREATE, objectMapper.writeValueAsBytes(user));
    }

    @Override
    public void sendUpdateMessage(UserDto user) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_UPDATE, objectMapper.writeValueAsBytes(user));
    }

    @Override
    public void sendDeleteMessage(UUID id) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_DELETE, objectMapper.writeValueAsBytes(id));
    }
}

package ru.vasshell.dservice.sender;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.vasshell.dservice.config.TopicConfig;
import ru.vasshell.dservice.dto.UserDto;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@AllArgsConstructor
public class RabbitUserSender implements UserSender {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendCreateMessage(UserDto user) {
        rabbitTemplate.convertAndSend(TopicConfig.QUEUE_CREATE, objectMapper.writeValueAsBytes(user));
    }

    @Override
    public void sendUpdateMessage(UserDto user) {
        rabbitTemplate.convertAndSend(TopicConfig.QUEUE_UPDATE, objectMapper.writeValueAsBytes(user));
    }

    @Override
    public void sendDeleteMessage(UUID id) {
        rabbitTemplate.convertAndSend(TopicConfig.QUEUE_DELETE, objectMapper.writeValueAsBytes(id));
    }
}

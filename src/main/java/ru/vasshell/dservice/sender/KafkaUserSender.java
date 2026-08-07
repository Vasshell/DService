package ru.vasshell.dservice.sender;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.vasshell.dservice.config.TopicConfig;
import ru.vasshell.dservice.dto.UserDto;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
@AllArgsConstructor
public class KafkaUserSender implements UserSender{
    
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper mapper;
    
    @Override
    public void sendCreateMessage(UserDto user) {
        kafkaTemplate.send(TopicConfig.QUEUE_CREATE, mapper.writeValueAsString(user));
    }

    @Override
    public void sendUpdateMessage(UserDto user) {
        kafkaTemplate.send(TopicConfig.QUEUE_UPDATE, mapper.writeValueAsString(user));
    }

    @Override
    public void sendDeleteMessage(UUID id) {
        kafkaTemplate.send(TopicConfig.QUEUE_DELETE, mapper.writeValueAsString(id));
    }
}

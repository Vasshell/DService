package ru.vasshell.dservice.listener;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.vasshell.dservice.config.TopicConfig;
import ru.vasshell.dservice.dto.UserDto;
import ru.vasshell.dservice.service.UserService;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
@AllArgsConstructor
public class KafkaUserListener {
    
    private final UserService userService;
    private final ObjectMapper objectMapper;
    
    @KafkaListener(topics = TopicConfig.QUEUE_CREATE)
    public void receiveCreateMessage(String user){
        userService.create(objectMapper.readValue(user, UserDto.class));
    }

    @KafkaListener(topics = TopicConfig.QUEUE_UPDATE)
    public void receiveUpdateMessage(String user){
        userService.update(objectMapper.readValue(user, UserDto.class));
    }
    
    @KafkaListener(topics = TopicConfig.QUEUE_DELETE)
    public void receiveDeleteMessage(String id){
        userService.delete(objectMapper.readValue(id, UUID.class));
    }
}

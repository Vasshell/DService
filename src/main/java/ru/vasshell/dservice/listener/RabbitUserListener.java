package ru.vasshell.dservice.listener;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import ru.vasshell.dservice.config.RabbitConfig;
import ru.vasshell.dservice.dto.UserDto;
import ru.vasshell.dservice.service.UserService;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RabbitUserListener {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfig.QUEUE_CREATE)
    public void receiveCreateMessage(Message message){
        userService.create(objectMapper.readValue(message.getBody(), UserDto.class));
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_UPDATE)
    public void receiveUpdateMessage(Message message){
        UserDto request = objectMapper.readValue(message.getBody(), UserDto.class);
        userService.update(request);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_DELETE)
    public void receiveDeleteMessage(Message message){
        userService.delete(objectMapper.readValue(message.getBody(), UUID.class));
    }
}

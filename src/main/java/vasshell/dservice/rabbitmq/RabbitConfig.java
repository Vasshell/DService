package vasshell.dservice.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_CREATE = "vasshell.dservice.user.create";
    public static final String QUEUE_DELETE = "vasshell.dservice.user.delete";
    public static final String QUEUE_UPDATE = "vasshell.dservice.user.patch";

    @Bean
    public Queue queueCreate(){
        return new Queue(QUEUE_CREATE);
    }

    @Bean
    public Queue queueDelete(){
        return new Queue(QUEUE_DELETE);
    }

    @Bean
    Queue queuePatch(){
        return new Queue(QUEUE_UPDATE);
    }

}

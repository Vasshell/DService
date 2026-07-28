package ru.vasshell.dservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.SpringDataJackson3Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

@Configuration
public class SerializationConfig {
    @Bean
    public ObjectMapper objectMapper(){
        PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Page.class).build();
        return JsonMapper.builder()
                .addModule(new SpringDataJackson3Configuration.PageModule(null))
                .activateDefaultTyping(validator)
                .build();
    }
}

package ru.vasshell.dservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import ru.vasshell.dservice.dto.PageResult;
import ru.vasshell.dservice.dto.UserDto;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManager redisCacheConfiguration(RedisConnectionFactory connectionFactory, ObjectMapper cacheMapper) {

        JavaType usersPageType = cacheMapper.getTypeFactory()
                .constructParametricType(PageResult.class, UserDto.class);

        RedisCacheConfiguration usersConf = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new JacksonJsonRedisSerializer<>(cacheMapper, usersPageType)
                        )
                );

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration("users", usersConf)
                .build();
    }

}

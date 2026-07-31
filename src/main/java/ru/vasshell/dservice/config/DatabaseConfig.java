package ru.vasshell.dservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vasshell.dservice.util.DatabaseInitializer;

@Getter
@Component
public class DatabaseConfig {
    private final String url;
    private final String username;
    private final String password;

    public DatabaseConfig(
            @Value("${spring.datasource.url}")
            String url,
            @Value("${spring.datasource.username}")
            String username,
            @Value("${spring.datasource.password}")
            String password
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
        DatabaseInitializer.verifyDbAndSchema(url, username, password);
    }

}

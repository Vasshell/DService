package ru.vasshell.dservice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vasshell.dservice.util.DatabaseInitializer;

import javax.sql.DataSource;

@Getter
@Configuration
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

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        
        return new HikariDataSource(config);
    }
}

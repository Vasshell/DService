package ru.vasshell.dservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    public static String PASSWORD;
    public static String USERNAME;
    public static String URL;

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        PASSWORD = password;
    }

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        USERNAME = username;
    }

    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        URL = url;
    }
}

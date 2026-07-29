package ru.vasshell.dservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.vasshell.dservice.config.DatabaseConfig;
import ru.vasshell.dservice.entity.User;
import ru.vasshell.dservice.mapper.UserMapper;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl  implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public void save(User user) {
    }

    @Override
    public Optional<User> findById(UUID id) {
        try {
            Connection connection = DriverManager.getConnection(
                    DatabaseConfig.URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * FROM users WHERE id = '%s'", id.toString()));
            if  (resultSet.next()) {
                return Optional.of(userMapper.toEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void saveAll(List<User> list) {

    }
}

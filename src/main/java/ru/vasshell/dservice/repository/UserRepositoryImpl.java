package ru.vasshell.dservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import ru.vasshell.dservice.config.DatabaseConfig;
import ru.vasshell.dservice.dto.UserFilterDto;
import ru.vasshell.dservice.entity.User;
import ru.vasshell.dservice.mapper.UserMapper;

import java.sql.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl  implements UserRepository {

    private final UserMapper userMapper;
    private final String selectByIdQuery = """
                            SELECT *
                            FROM users
                            WHERE id = '%s'
                            """;
    private final String selectQuery = """
                            SELECT *
                            FROM users
                            %s
                            LIMIT %d OFFSET %d
                            """;

    @Override
    public void save(User user) {
    }

    @Override
    public Optional<User> findById(UUID id) {
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    String.format(selectByIdQuery, id.toString()));
            return resultSet.next()
                    ? Optional.of(userMapper.toEntity(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public Page<User> findAll(UserFilterDto filters, Pageable pageable) {
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    String.format(selectQuery, getSortingClause(pageable.getSort()), pageable.getPageSize(), pageable.getOffset()));
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(userMapper.toEntity(resultSet));
            }
            ResultSet totalCount = statement.executeQuery("SELECT COUNT(*) FROM users");
            long total = totalCount.next() ? totalCount.getLong(1) : result.size();
            return new PageImpl<>(result,  pageable, total);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void saveAll(List<User> list) {

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
    }

    private String getSortingClause(Sort sort) {
        StringBuilder clause = new StringBuilder();
        clause.append("ORDER BY ");
        if (sort.isUnsorted()){
            clause.append("id ASC");
            return clause.toString();
        }
        StringJoiner joiner = new StringJoiner(", ");
        for (Sort.Order order : sort) {
            joiner.add(order.getProperty() + " " + order.getDirection());
        }
        return clause.append(joiner).toString();
    }
}

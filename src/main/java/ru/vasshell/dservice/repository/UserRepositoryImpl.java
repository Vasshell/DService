package ru.vasshell.dservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.vasshell.dservice.config.DatabaseConfig;
import ru.vasshell.dservice.dto.UserFilterDto;
import ru.vasshell.dservice.entity.User;
import ru.vasshell.dservice.mapper.UserMapper;
import ru.vasshell.dservice.util.UserClause;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl  implements UserRepository {

    private final UserMapper userMapper;
    private final DataSource source;

    @Override
    public void ensure() {
        String ensureTableQuery = """
                CREATE TABLE IF NOT EXISTS users(
                    id uuid not null primary key default uuidv7(),
                    first_name varchar(255),
                    last_name varchar(255),
                    age integer
                )
                """;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            statement.executeUpdate(ensureTableQuery);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public Optional<User> findById(UUID id) {
        String selectByIdQuery = """
                    SELECT id, first_name, last_name, age
                    FROM users
                    WHERE id = ?
                    """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectByIdQuery)){
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(userMapper.toEntity(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<User> findAll(UserFilterDto filters, Pageable pageable) {
        UserClause userClause = UserClause.getFilteringClause(filters);
        String selectQuery = """
                    SELECT id, first_name, last_name, age
                    FROM users
                    %s
                    %s
                    LIMIT ? OFFSET ?
                    """.formatted(userClause.getClause(), UserClause.getSortingClause(pageable.getSort()));
        String countQuery = """
                    SELECT COUNT(*)
                    FROM users
                    %s
                    """.formatted(userClause.getClause());

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             PreparedStatement countStatement = connection.prepareStatement(countQuery)){

            int i = 1;
            for (Object obj : userClause.getArgs()){
                statement.setObject(i, obj);
                countStatement.setObject(i, obj);
                i++;
            }
            statement.setLong(i, pageable.getPageSize());
            statement.setLong(i + 1, pageable.getOffset());
            try (ResultSet resultSet = statement.executeQuery();
            ResultSet totalCount = countStatement.executeQuery()) {
                List<User> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(userMapper.toEntity(resultSet));
                }
                long total = totalCount.next() ? totalCount.getLong(1) : result.size();
                return new PageImpl<>(result,  pageable, total);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String selectAllQuery = """
                                SELECT id, first_name, last_name, age
                                FROM users
                                """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllQuery)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<User> results = new ArrayList<>();
                while (resultSet.next()) results.add(userMapper.toEntity(resultSet));
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<User> list) {
        String insertQuery ="""
                INSERT INTO users (first_name, last_name, age)
                VALUES (?,?,?)
                """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)){
            for (User user : list){
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setInt(3, user.getAge());
                statement.addBatch();
            }
            statement.executeBatch();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) saveAll(List.of(user));
    }

    @Override
    public void update(User user) {
        String updateQuery = """
                UPDATE users
                SET first_name = ?,
                last_name = ?,
                age = ?
                WHERE id = ?
                """;
        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(updateQuery)){
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setObject(4, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String deleteByIdQuery = """
                DELETE
                FROM users
                WHERE id = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteByIdQuery)){
            statement.setObject(1, id);
            statement.execute();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

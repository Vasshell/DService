package ru.vasshell.dservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vasshell.dservice.dto.UserFilterDto;
import ru.vasshell.dservice.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    Page<User> findAll(UserFilterDto filters, Pageable pageable);
    List<User> findAll();
    void save(User user);
    void saveAll(List<User> list);
    void update(User entity);
    void deleteById(UUID id);
    void ensure();
}

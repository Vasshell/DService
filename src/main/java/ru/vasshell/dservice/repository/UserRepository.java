package ru.vasshell.dservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vasshell.dservice.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(UUID id);

    void deleteById(UUID id);

    Page<User> findAll(Pageable pageable);
    List<User> findAll();

    void saveAll(List<User> list);
}

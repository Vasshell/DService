package ru.vasshell.dservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vasshell.dservice.dto.UserDto;
import ru.vasshell.dservice.dto.UserFilterDto;
import ru.vasshell.dservice.sender.UserSender;
import ru.vasshell.dservice.service.UserService;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserSender publisher;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        log.info("Received GET request at /api/users/{}", id);
        return userService.getById(id)
                .map(body -> {
                    log.debug("User found at id: {}", id);
                    return ResponseEntity.ok(body);
                })
                .orElseGet(() -> {
                    log.debug("User not found at id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(@SpringQueryMap UserFilterDto params, Pageable pageable) {
        Page<UserDto> response = userService.getAll(params, pageable).toPage(pageable); //clunky fix?
        log.info("Received GET request at /api/users, filter: {}, pageable: {}", params, pageable);
        if (response.isEmpty()){
            log.debug("No users found");
            return ResponseEntity.noContent().build();
        }
        log.debug("{} users found", response.getNumberOfElements());
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> create(@RequestBody @Validated(UserDto.OnCreate.class) UserDto user) {
        log.info("Received POST request at /api/users, firstName: {}, lastName: {}, age: {}",
                user.getFirstName(), user.getLastName(), user.getAge());
        publisher.sendCreateMessage(user);
        log.debug("Create User message sent");
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> update(@RequestBody @Validated(UserDto.OnUpdate.class) UserDto user, @PathVariable UUID id) {
        log.info("Received PUT request at /api/users/{}, firstName: {}, lastName: {}, age: {}",
                id, user.getFirstName(), user.getLastName(), user.getAge());
        if (!user.getId().equals(id)) {
            log.debug("PUT request rejected: ID's are not matching");
            return ResponseEntity.badRequest().body("ID's are not matching");
        }
        publisher.sendUpdateMessage(user);
        log.debug("Update User message sent");
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        log.info("Received DELETE request at /api/users/{}", id);
        publisher.sendDeleteMessage(id);
        log.debug("Delete User message sent");
        return ResponseEntity.accepted().build();
    }

}

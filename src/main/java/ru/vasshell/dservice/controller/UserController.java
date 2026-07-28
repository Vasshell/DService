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
        return userService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(@SpringQueryMap UserFilterDto params, Pageable pageable) {
        Page<UserDto> response = userService.getAll(params, pageable);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> create(@RequestBody @Validated(UserDto.OnCreate.class) UserDto user) {
        publisher.sendCreateMessage(user);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> update(@RequestBody @Validated(UserDto.OnUpdate.class) UserDto user, @PathVariable UUID id) {
        if (!user.getId().equals(id)) {
            return ResponseEntity.badRequest().body("ID's are not matching");
        }
        publisher.sendUpdateMessage(user);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        publisher.sendDeleteMessage(id);
        return ResponseEntity.accepted().build();
    }

}

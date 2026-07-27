package vasshell.dservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vasshell.dservice.dto.UserDto;
import vasshell.dservice.dto.UserFilterDto;
import vasshell.dservice.sender.UserSender;
import vasshell.dservice.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserSender publisher;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> create(@RequestBody @Validated(UserDto.OnCreate.class) UserDto user){
        publisher.sendCreateMessage(user);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id){

        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->
                        ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAll(@SpringQueryMap UserFilterDto params){
        List<UserDto> response = userService.getAll(params);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }


    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> update(@RequestBody @Validated(UserDto.OnUpdate.class) UserDto user,
                                                     @PathVariable UUID id){
        if (!user.id().equals(id)) {
            return ResponseEntity.badRequest().body("ID are not matching");
        }
        publisher.sendUpdateMessage(user);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        publisher.sendDeleteMessage(id);
        return ResponseEntity.accepted().build();
    }

}

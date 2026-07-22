package vasshell.dservice.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vasshell.dservice.dto.*;
import vasshell.dservice.publisher.UserMessagePublisher;
import vasshell.dservice.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMessagePublisher publisher;

    public UserController(UserService userService, UserMessagePublisher publisher){
        this.userService = userService;
        this.publisher = publisher;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getById(@PathVariable UUID id){

        return userService.findById(id)
                .map(user ->
                        new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(()->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<?> getAll(UserFilterParamsDto params,
                                    @RequestParam(defaultValue = "5", name = ("page_size")) int pageSize,
                                    @RequestParam(defaultValue = "0", name = ("page_num")) int pageNum){
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        List<UserGetDto> response = userService.findAll(params, pageRequest);
        return response.isEmpty()
                ? new ResponseEntity<>("Nothing found", HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserCreateDto user){
        publisher.createUserMessage(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id){
        publisher.deleteUserMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> modifyUser(@RequestBody UserCreateDto user,
                                              @PathVariable UUID id){
        UserUpdateDto userUpdateDto = new UserUpdateDto(id, user);
        publisher.updateUserMessage(userUpdateDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> replaceUser(@RequestBody @Valid UserCreateDto user,
                                                     @PathVariable UUID id){
        UserUpdateDto userUpdateDto = new UserUpdateDto(id, user);
        publisher.updateUserMessage(userUpdateDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

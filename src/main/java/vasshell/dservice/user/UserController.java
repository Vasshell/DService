package vasshell.dservice.user;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vasshell.dservice.user.dto.CreateUserDto;
import vasshell.dservice.user.dto.GetUserDto;
import vasshell.dservice.user.dto.UpdateUserDto;
import vasshell.dservice.user.dto.UserSearchFilters;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMessagingService messagingService;

    public UserController(UserService userService, UserMessagingService messagingService){
        this.userService = userService;
        this.messagingService = messagingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getById(@PathVariable UUID id){

        return userService.findById(id)
                .map(user ->
                        new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(()->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<GetUserDto>> getAll(UserSearchFilters params,
                                                @RequestParam(defaultValue = "5", name = ("page_size")) int pageSize,
                                                @RequestParam(defaultValue = "0", name = ("page_num")) int pageNum){
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        return new ResponseEntity<>(userService.findAll(params, pageRequest), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserDto user){
        messagingService.createUserMessage(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id){
        messagingService.deleteUserMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> modifyUser(@RequestBody CreateUserDto user,
                                              @PathVariable UUID id){
        UpdateUserDto updateUserDto = new UpdateUserDto(id, user);
        messagingService.updateUserMessage(updateUserDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> replaceUser(@RequestBody @Valid CreateUserDto user,
                                                     @PathVariable UUID id){
        UpdateUserDto updateUserDto = new UpdateUserDto(id, user);
        messagingService.updateUserMessage(updateUserDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

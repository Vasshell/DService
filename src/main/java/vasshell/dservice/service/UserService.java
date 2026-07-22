package vasshell.dservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.dto.*;
import vasshell.dservice.entity.User;
import vasshell.dservice.mapper.UserMapper;
import vasshell.dservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper mapper;

    public UserService(UserRepository userRepo, UserMapper mapper, ObjectMapper objectMapper){
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    public Optional<UserGetDto> findById(UUID id) {
        return userRepo.findById(id)
                .map(mapper::userToGetDto);
    }

    public List<UserGetDto> findAll(UserFilterParamsDto params, Pageable pageable){
        return mapper.userToGetDto(
                userRepo.findAll(params.toSpecification(), pageable)
                        .getContent());
    }

    public void createUser(User user) {
        userRepo.save(user);
    }

    @Async
    public void createUser(UserCreateDto user){
        createUser(mapper.createDtoToUser(user));
    }

    @Async
    public void deleteUser(UUID id) {
        userRepo.deleteById(id);
    }

    @Async
    public void updateUser(UserUpdateDto userUpdateDto){
        Optional<User> userSearch = userRepo.findById(userUpdateDto.id());
        if (userSearch.isEmpty()){
            return;
        }
        User userToUpdate = userSearch.get();
        if (userUpdateDto.firstName() != null) userToUpdate.setFirstName(userUpdateDto.firstName());
        if (userUpdateDto.lastName() != null) userToUpdate.setLastName(userUpdateDto.lastName());
        if (userUpdateDto.age() != null) userToUpdate.setAge(userUpdateDto.age());

        userRepo.save(userToUpdate);
    }
}

package vasshell.dservice.user;

import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import vasshell.dservice.user.dto.CreateUserDto;
import vasshell.dservice.user.dto.GetUserDto;
import vasshell.dservice.user.dto.UpdateUserDto;
import vasshell.dservice.user.dto.UserSearchFilters;

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

    public Optional<GetUserDto> findById(UUID id) {
        return userRepo.findById(id)
                .map(mapper::userToGetDto);
    }

    public List<GetUserDto> findAll(UserSearchFilters params, Pageable pageable){
        return mapper.userToGetDto(
                userRepo.findAll(params.toSpecification(), pageable)
                        .getContent());
    }

    public void createUser(User user) {
        userRepo.save(user);
    }

    @Async
    public void createUser(CreateUserDto user){
        createUser(mapper.createDtoToUser(user));
    }

    @Async
    public void deleteUser(UUID id) {
        userRepo.deleteById(id);
    }

    @Async
    public void updateUser(UpdateUserDto updateUserDto){
        Optional<User> userSearch = userRepo.findById(updateUserDto.id());
        if (userSearch.isEmpty()){
            return;
        }
        User userToUpdate = userSearch.get();
        if (updateUserDto.firstName() != null) userToUpdate.setFirstName(updateUserDto.firstName());
        if (updateUserDto.lastName() != null) userToUpdate.setLastName(updateUserDto.lastName());
        if (updateUserDto.age() != null) userToUpdate.setAge(updateUserDto.age());

        userRepo.save(userToUpdate);
    }
}

package vasshell.dservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;
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
                userRepo.findAll(toSpecification(params), pageable)
                        .getContent());
    }

    public void createUser(User user) {
        userRepo.save(user);
    }

    @Async
    public void createUser(UserDto user){
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

    private Specification<User> toSpecification(UserFilterParamsDto params){
        Specification<User> spec = Specification.unrestricted();

        if (params.firstName() != null){
            spec = spec.and(UserSpecification.likeFirstName(params.firstName()));
        }
        if (params.lastName() != null){
            spec = spec.and(UserSpecification.likeLastName(params.lastName()));
        }
        if (params.age() != null){
            spec = spec.and(UserSpecification.hasAge(params.age()));
        }
        if (params.ageGreaterThan()!=null){
            spec = spec.and(UserSpecification.hasAgeGreaterThan(params.ageGreaterThan()));
        }
        if (params.ageLessThan()!=null){
            spec = spec.and(UserSpecification.hasAgeLessThan(params.ageLessThan()));
        }
        return spec;
    }
}

class UserSpecification {

    public static PredicateSpecification<User> likeFirstName(String firstName){
        return funnyThing(firstName, "firstName");
    }

    public static PredicateSpecification<User> likeLastName(String lastName){
        return funnyThing(lastName, "lastName");
    }

    private static PredicateSpecification<User> funnyThing(String compareTo, String propertyName){
        return (root, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(propertyName)), "%" + compareTo.toLowerCase() + "%");
    }

    public static PredicateSpecification<User> hasAge(Integer age) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.equal(from.get("age"), age);
    }

    public static PredicateSpecification<User> hasAgeGreaterThan(Integer ageGt) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.greaterThan(from.get("age"), ageGt);
    }

    public static PredicateSpecification<User> hasAgeLessThan(Integer ageLt) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.lessThan(from.get("age"), ageLt);
    }
}

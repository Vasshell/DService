package vasshell.dservice.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vasshell.dservice.dto.UserDto;
import vasshell.dservice.dto.UserFilterDto;
import vasshell.dservice.entity.User;
import vasshell.dservice.mapper.UserMapper;
import vasshell.dservice.repository.UserRepository;
import vasshell.dservice.util.UserSpecification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper mapper;

    @CacheEvict(value = "users", allEntries = true)
    public void create(UserDto user){
        userRepo.save(mapper.dtoToEntity(user));
    }

    public Optional<UserDto> getById(UUID id) {
        return userRepo.findById(id)
                .map(mapper::entityToDto);
    }

    @Cacheable(cacheNames = "users")
    public List<UserDto> getAll(UserFilterDto params){
        PageRequest pageable = PageRequest.of(params.pageNum(), params.pageSize());
        return userRepo.findAll(paramsToSpec(params), pageable)
                        .getContent()
                        .stream().map(mapper::entityToDto).toList();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(UserDto userDto){
        Optional<User> userSearch = userRepo.findById(userDto.id());
        if (userSearch.isEmpty()){
            return;
        }
        User userToUpdate = userSearch.get();
        if (userDto.firstName() != null) userToUpdate.setFirstName(userDto.firstName());
        if (userDto.lastName() != null) userToUpdate.setLastName(userDto.lastName());
        if (userDto.age() != null) userToUpdate.setAge(userDto.age());

        userRepo.save(userToUpdate);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(UUID id) {
        userRepo.deleteById(id);
    }

    private Specification<User> paramsToSpec(UserFilterDto params){
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

    @Profile("test")
    @PostConstruct
    private void init(){
        if (!userRepo.findAll(Specification.unrestricted()).isEmpty()) {
            return;
        }
        List<User> list = List.of(
                new User(null, "Иван", "Иванов", 28),
                new User(null, "Мария", "Петрова", 34),
                new User(null, "Алексей", "Смирнов", 22),
                new User(null, "Елена", "Кузнецова", 45),
                new User(null, "Дмитрий", "Попов", 31),
                new User(null, "Анна", "Соколова", 29),
                new User(null, "Сергей", "Лебедев", 40),
                new User(null, "Ольга", "Козлова", 26),
                new User(null, "Михаил", "Новиков", 53),
                new User(null, "Татьяна", "Морозова", 37),
                new User(null, "Иван", "Иванов", 28),
                new User(null, "Мария", "Петрова", 34),
                new User(null, "Сергей", "Смирнов", 22),
                new User(null, "Елена", "Кузнецова", 45),
                new User(null, "Сергей", "Попов", 31),
                new User(null, "Анна", "Соколова", 29),
                new User(null, "Сергей", "Лебедев", 40),
                new User(null, "Ольга", "Козлова", 26),
                new User(null, "Сергей", "Новиков", 53),
                new User(null, "Татьяна", "Морозова", 37),
                new User(null, "Настасья", "Говнова", 67),
                new User(null, "Иван", "Говнов", 52),
                new User(null, "Сикс", "Севен", 67)
        );

        userRepo.saveAll(list);
    }
}

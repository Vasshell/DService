package ru.vasshell.dservice.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.vasshell.dservice.dto.UserDto;
import ru.vasshell.dservice.dto.UserFilterDto;
import ru.vasshell.dservice.entity.User;
import ru.vasshell.dservice.mapper.UserMapper;
import ru.vasshell.dservice.repository.UserRepository;
import ru.vasshell.dservice.util.UserSpecification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper mapper;

    public Optional<UserDto> getById(UUID id) {
        return userRepo.findById(id).map(mapper::entityToDto);
    }

    @Cacheable(cacheNames = "users", key = "#params + ' size='+ #pageable.pageSize +' page='+ #pageable.pageNumber")
    public Page<UserDto> getAll(UserFilterDto params, Pageable pageable){
        return userRepo.findAll(paramsToSpec(params), pageable).map(mapper::entityToDto);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void create(UserDto user){
        userRepo.save(mapper.dtoToEntity(user));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(UserDto userDto){
        Optional<User> userSearch = userRepo.findById(userDto.getId());
        if (userSearch.isEmpty()){
            return;
        }
        userRepo.save(mapper.dtoToEntity(userDto));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(UUID id) {
        userRepo.deleteById(id);
    }

    private Specification<User> paramsToSpec(UserFilterDto params){
        Specification<User> spec = Specification.unrestricted();

        if (params.getFirstName() != null){
            spec = spec.and(UserSpecification.likeFirstName(params.getFirstName()));
        }
        if (params.getLastName() != null){
            spec = spec.and(UserSpecification.likeLastName(params.getLastName()));
        }
        if (params.getAge() != null){
            spec = spec.and(UserSpecification.hasAge(params.getAge()));
        }
        if (params.getAgeGreaterThan()!=null){
            spec = spec.and(UserSpecification.hasAgeGreaterThan(params.getAgeGreaterThan()));
        }
        if (params.getAgeLessThan()!=null){
            spec = spec.and(UserSpecification.hasAgeLessThan(params.getAgeLessThan()));
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

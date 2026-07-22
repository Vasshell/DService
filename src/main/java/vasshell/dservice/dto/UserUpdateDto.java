package vasshell.dservice.dto;

import java.util.UUID;

public record UserUpdateDto(UUID id,
                            String firstName,
                            String lastName,
                            Integer age)
{
    public UserUpdateDto(UUID id, UserCreateDto dto) {
        this(id, dto.firstName(), dto.lastName(), dto.age());
    }
}


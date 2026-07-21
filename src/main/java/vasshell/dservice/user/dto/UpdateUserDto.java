package vasshell.dservice.user.dto;

import java.util.UUID;

public record UpdateUserDto(UUID id,
                            String firstName,
                            String lastName,
                            Integer age)
{
    public UpdateUserDto(UUID id, CreateUserDto dto) {
        this(id, dto.firstName(), dto.lastName(), dto.age());
    }
}


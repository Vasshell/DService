package vasshell.dservice.user.dto;


import java.util.UUID;

public record GetUserDto (
        UUID id,
        String firstName,
        String lastName,
        Integer age)
{ }

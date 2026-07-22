package vasshell.dservice.dto;


import java.util.UUID;

public record UserGetDto(
        UUID id,
        String firstName,
        String lastName,
        Integer age)
{ }

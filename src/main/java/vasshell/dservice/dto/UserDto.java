package vasshell.dservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserDto(
        @NotNull
        UUID id,
        @NotBlank(message = "First name should not be blank")
        String firstName,
        @NotBlank(message = "Last name should not be blank")
        String lastName,
        @NotNull
        Integer age)
{ }

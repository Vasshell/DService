package vasshell.dservice.user.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank(message = "First name should not be blank")
        String firstName,
        @NotBlank(message = "Last name should not be blank")
        String lastName,
        Integer age)
{ }

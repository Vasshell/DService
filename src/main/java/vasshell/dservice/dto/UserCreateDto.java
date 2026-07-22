package vasshell.dservice.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
        @NotBlank(message = "First name should not be blank")
        String firstName,
        @NotBlank(message = "Last name should not be blank")
        String lastName,
        Integer age)
{ }

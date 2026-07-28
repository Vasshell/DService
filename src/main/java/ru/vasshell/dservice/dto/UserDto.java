package ru.vasshell.dservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.util.UUID;

@Data
public final class UserDto {
    private final @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class) UUID id;
    private final @NotBlank(message = "First name should not be blank") String firstName;
    private final @NotBlank(message = "Last name should not be blank") String lastName;
    private final @NotNull Integer age;

    public UserDto(
            @Null(groups = OnCreate.class)
            @NotNull(groups = OnUpdate.class)
            UUID id,
            @NotBlank(message = "First name should not be blank")
            String firstName,
            @NotBlank(message = "Last name should not be blank")
            String lastName,
            @NotNull
            Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public interface OnUpdate {
    }

    public interface OnCreate {
    }
}

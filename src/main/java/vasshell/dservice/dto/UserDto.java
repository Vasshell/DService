package vasshell.dservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.UUID;

public record UserDto(
        @Null(groups = OnCreate.class)
        @NotNull(groups = OnUpdate.class)
        UUID id,
        @NotBlank(message = "First name should not be blank")
        String firstName,
        @NotBlank(message = "Last name should not be blank")
        String lastName,
        @NotNull
        Integer age)
{
        public interface OnUpdate{}
        public interface OnCreate{}
}

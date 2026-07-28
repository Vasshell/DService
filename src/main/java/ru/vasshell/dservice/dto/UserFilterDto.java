package ru.vasshell.dservice.dto;

import lombok.Data;

@Data
public final class UserFilterDto {
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final Integer ageGreaterThan;
    private final Integer ageLessThan;
}

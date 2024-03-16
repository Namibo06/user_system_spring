package com.apijava.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRecordDto(
        @NotBlank String first_name,
        String last_name,
        @NotBlank @Email String email,
        @NotBlank String nivel_user
) {
}

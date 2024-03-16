package com.apijava.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(
        @NotBlank String first_name,
        String last_name,
        @NotBlank @Email String email,
        @NotBlank String nivel_user,
        @NotBlank String password
        ) {
    /*
    * record define uma classe como imutavel,ou seja,depois de criada não pode ser alterada
    * */
}

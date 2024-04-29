package com.test_task.project.dto.user;

import com.test_task.project.validation.email.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UserRegistrationRequestDto(
        @Email
        String email,
        @Size(min=6, max=50)
        String password,
        @Size(min=6, max=50)
        String repeatedPassword,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        LocalDate birthDate,
        String address,
        String phoneNumber) {
}

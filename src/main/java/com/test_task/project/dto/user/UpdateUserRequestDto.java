package com.test_task.project.dto.user;

import com.test_task.project.validation.email.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDto(@Email
                                   String email,
                                   @NotBlank
                                   String firstName,
                                   @NotBlank
                                   String lastName,
                                   String address,
                                   String phoneNumber) {
}

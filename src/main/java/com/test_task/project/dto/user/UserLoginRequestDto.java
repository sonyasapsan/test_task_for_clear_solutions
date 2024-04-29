package com.test_task.project.dto.user;

import com.test_task.project.validation.email.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(@Email
                                  String email,
                                  @NotBlank
                                  @Size(min = 8, max = 20)
                                  String password
) {
}
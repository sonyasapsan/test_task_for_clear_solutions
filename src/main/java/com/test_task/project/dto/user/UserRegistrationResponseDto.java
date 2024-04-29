package com.test_task.project.dto.user;

public record UserRegistrationResponseDto(Long id,
                                          String email,
                                          String firstName,
                                          String lastName) {
}

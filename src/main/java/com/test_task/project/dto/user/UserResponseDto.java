package com.test_task.project.dto.user;

import java.time.LocalDate;

public record UserResponseDto(String email,
                              String firstName,
                              String lastName,
                              LocalDate birthDate,
                              String address,
                              String phoneNumber) {
}

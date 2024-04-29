package com.test_task.project.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserAddressDto(@NotBlank
                                   String address) {
}

package com.test_task.project.dto.user;

import com.test_task.project.validation.phone_number.Phone;

public record UpdateUserPhoneNumberDto(@Phone
                                       String phoneNumber) {
}

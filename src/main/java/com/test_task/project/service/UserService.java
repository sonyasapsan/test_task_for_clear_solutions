package com.test_task.project.service;

import com.test_task.project.dto.user.*;
import com.test_task.project.exception.RegistrationException;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;

    UserResponseDto updateUserPhoneNumber(UpdateUserPhoneNumberDto phoneNumberDto, Long id);

    UserResponseDto updateUserAddress(UpdateUserAddressDto addressDto, Long id);

    UserResponseDto update(UpdateUserRequestDto requestDto, Long id);

    List<UserResponseDto> getAllUsersByDateRange(LocalDate from, LocalDate to, Pageable pageable);

    void deleteById(Long id);
}

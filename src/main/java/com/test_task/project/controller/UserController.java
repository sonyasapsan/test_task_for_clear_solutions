package com.test_task.project.controller;

import com.test_task.project.dto.user.*;
import com.test_task.project.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/birthDateRange/{from}/{to}")
    public List<UserResponseDto> findAllUsersByBirthDateRange(@PathVariable LocalDate from,
                                                              @PathVariable LocalDate to,
                                                              @PageableDefault Pageable pageable) {
        return userService.getAllUsersByDateRange(from, to, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Positive Long id) {
        userService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserResponseDto update(@RequestBody @Valid
                                      UpdateUserRequestDto requestDto,
                                  @PathVariable @Positive Long id) {
        return userService.update(requestDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/address")
    public UserResponseDto updateUserAddress(@RequestBody @Valid UpdateUserAddressDto updateUserAddressDto,
                                             @Positive @PathVariable Long id) {
        return userService.updateUserAddress(updateUserAddressDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/phone-number")
    public UserResponseDto updateUserPhoneNumber(@RequestBody @Valid UpdateUserPhoneNumberDto updateUserPhoneNumberDto,
                                             @Positive @PathVariable Long id) {
        return userService.updateUserPhoneNumber(updateUserPhoneNumberDto, id);
    }
}

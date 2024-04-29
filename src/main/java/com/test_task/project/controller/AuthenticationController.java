package com.test_task.project.controller;

import com.test_task.project.dto.user.UserLoginRequestDto;
import com.test_task.project.dto.user.UserLoginResponseDto;
import com.test_task.project.dto.user.UserRegistrationRequestDto;
import com.test_task.project.dto.user.UserRegistrationResponseDto;
import com.test_task.project.exception.RegistrationException;
import com.test_task.project.security.AuthenticationService;
import com.test_task.project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Validated
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        return authenticationService.authenticate(userLoginRequestDto);
    }

    @PostMapping("/register")
    public UserRegistrationResponseDto register(@RequestBody @Valid
                                                UserRegistrationRequestDto userRegistrationRequestDto)
                                                throws RegistrationException {
        return userService.register(userRegistrationRequestDto);
    }
}

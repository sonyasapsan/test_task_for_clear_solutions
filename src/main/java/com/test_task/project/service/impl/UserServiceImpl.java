package com.test_task.project.service.impl;

import com.test_task.project.dto.user.*;
import com.test_task.project.exception.EntityNotFoundException;
import com.test_task.project.exception.InvalidDataException;
import com.test_task.project.exception.RegistrationException;
import com.test_task.project.repository.RoleRepository;
import com.test_task.project.mapper.UserMapper;
import com.test_task.project.model.Role;
import com.test_task.project.model.User;
import com.test_task.project.repository.UserRepository;
import com.test_task.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final static String NO_SUCH_USER_ID_MESSAGE = "There is no user with such id: ";
    private final static String UNABLE_REGISTRATION = "Unable to complete registration";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private Role role;
    @Value("${min.age}")
    private int MIN_AGE;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request) throws RegistrationException {
        if (request.birthDate().plusYears(MIN_AGE).isAfter(LocalDate.now())) {
            throw new RegistrationException("You're too young. Please, grow up :)\n"
                    + UNABLE_REGISTRATION);
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RegistrationException(UNABLE_REGISTRATION);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        role = roleRepository.findRoleByName(Role.RoleName.ROLE_USER);
        user.setRoles(Set.of(role));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserResponseDto> getAllUsersByDateRange(LocalDate from, LocalDate to, Pageable pageable) {
        if (from.isAfter(to)) {
            throw new InvalidDataException("Wrong dates for range.");
        }
        return userRepository.getAllUsersByDateRange(from, to, pageable).getContent().stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public UserResponseDto update(UpdateUserRequestDto requestDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(NO_SUCH_USER_ID_MESSAGE + id)
        );
        userMapper.updateUserFromDto(requestDto, user);
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateUserAddress(UpdateUserAddressDto addressDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USER_ID_MESSAGE + id));
        user.setAddress(addressDto.address());
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateUserPhoneNumber(UpdateUserPhoneNumberDto phoneNumberDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_SUCH_USER_ID_MESSAGE + id));
        user.setPhoneNumber(phoneNumberDto.phoneNumber());
        return userMapper.toUserResponseDto(userRepository.save(user));
    }
}

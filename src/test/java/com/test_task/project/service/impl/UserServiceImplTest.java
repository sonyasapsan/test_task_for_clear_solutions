package com.test_task.project.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.test_task.project.dto.user.*;
import com.test_task.project.mapper.UserMapper;
import com.test_task.project.mapper.impl.UserMapperImpl;
import com.test_task.project.model.Role;
import com.test_task.project.model.User;
import com.test_task.project.repository.RoleRepository;
import com.test_task.project.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Spy
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper = new UserMapperImpl();
    @Spy
    private RoleRepository roleRepository;
    @Spy
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save new user")
    void register_validCase_Ok() {
        UserRegistrationRequestDto requestDto = getRegistrationRequest();
        User user = getUser();
        HashSet<Role> roles = getRoles();
        Mockito.when(userRepository.existsByEmail(requestDto.email())).thenReturn(false);
        Mockito.when(userMapper.toUser(requestDto)).thenReturn(user);
        Mockito.when(roleRepository.findRoleByName(Role.RoleName.ROLE_USER))
                .thenReturn((Role) roles.toArray()[0]);
        user.setRoles(roles);
        UserRegistrationResponseDto expected = new UserRegistrationResponseDto(
                1L, "soniascott@gmail.com", "Sonia", "Scott"
        );
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toUserResponse(user)).thenReturn(expected);
        UserRegistrationResponseDto actual = userService.register(requestDto);
        verify(passwordEncoder, times(1)).encode(requestDto.password());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete the user by certain id")
    void deleteById_validCase_deleted() {
        Long userId = 1L;
        userService.deleteById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Get all users by birth date range")
    void getAllUsersByDateRange_validCase_Ok() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> users = getUserList();
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());
        LocalDate from = LocalDate.of(2004, 1, 1);
        LocalDate to = LocalDate.of(2006, 1, 1);
        UserResponseDto firstUser = new UserResponseDto("soniascott@gmail.com", "Sonia",
                "Scott", LocalDate.of(2004, 3, 3),
                "Lviv, Street Open", "+0994873436");
        UserResponseDto secondUser = new UserResponseDto("bobbrown@gmail.com", "Bob",
                "Brown", LocalDate.of(2005, 5, 15),
                "Kyiv, Hreshchtatik", "+0994776421");
        Mockito.when(userRepository.getAllUsersByDateRange(from, to, pageable)).thenReturn(userPage);
        List<UserResponseDto> expected = List.of(firstUser, secondUser);
        List<UserResponseDto> actual = userService.getAllUsersByDateRange(from, to, pageable);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Update user fully")
    void update_validCase_Ok() {
        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto("soniascott@gmail.com",
                "Sofia", "Scotty", "Lviv, Street Open", "+0994873436");
        User updatedUser = getUser();
        Long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(updatedUser));
        updatedUser.setFirstName("Sofia");
        updatedUser.setFirstName("Scotty");
        UserResponseDto expected = new UserResponseDto("soniascott@gmail.com",
                "Sofia", "Scotty", LocalDate.of(2004, 3, 3),
                "Lviv, Street Open", "+0994873436");
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        Mockito.when(userMapper.toUserResponseDto(updatedUser)).thenReturn(expected);
        UserResponseDto actual = userService.update(updateUserRequestDto, 1L);
        verify(userMapper, times(1)).updateUserFromDto(updateUserRequestDto, updatedUser);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Update user's address")
    void updateUserAddress_validCase_Ok() {
        User user = getUser();
        Long userId = 1L;
        UpdateUserAddressDto addressDto = new UpdateUserAddressDto("London, 9/13");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        user.setAddress(addressDto.address());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserResponseDto expected = new UserResponseDto("soniascott@gmail.com",
                "Sonia", "Scoty", LocalDate.of(2004, 3, 3),
                addressDto.address(), "+0994873436");
        Mockito.when(userMapper.toUserResponseDto(user)).thenReturn(expected);
        UserResponseDto actual = userService.updateUserAddress(addressDto, userId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update user's phone number")
    void updateUserPhoneNumber_validCase_Ok() {
        User user = getUser();
        Long userId = 1L;
        UpdateUserPhoneNumberDto phoneNumberDto = new UpdateUserPhoneNumberDto("+0994873666");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        user.setPhoneNumber(phoneNumberDto.phoneNumber());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserResponseDto expected = new UserResponseDto("soniascott@gmail.com",
                "Sonia", "Scoty", LocalDate.of(2004, 3, 3),
                "Lviv, Street Open", phoneNumberDto.phoneNumber());
        Mockito.when(userMapper.toUserResponseDto(user)).thenReturn(expected);
        UserResponseDto actual = userService.updateUserPhoneNumber(phoneNumberDto, userId);
        Assertions.assertEquals(expected, actual);
    }

    private static HashSet<Role> getRoles() {
        Role role = new Role();
        role.setName(Role.RoleName.ROLE_USER);
        role.setId(1L);
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }

    private static List<User> getUserList() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Sonia");
        user.setLastName("Scott");
        user.setBirthDate(LocalDate.of(2004, 3, 3));
        user.setEmail("soniascott@gmail.com");
        user.setPassword("password03032004");
        user.setAddress("Lviv, Street Open");
        user.setPhoneNumber("+0994873436");
        user.setRoles(getRoles());

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setFirstName("Bob");
        secondUser.setLastName("Brown");
        secondUser.setBirthDate(LocalDate.of(2005, 5, 15));
        secondUser.setEmail("bobbrown@gmail.com");
        secondUser.setPassword("password20050515");
        secondUser.setAddress("Kyiv, Hreshchtatik");
        secondUser.setPhoneNumber("+0994776421");
        secondUser.setRoles(getRoles());
        return List.of(user, secondUser);
    }

    private static User getUser() {
        return getUserList().get(0);
    }

    private static UserRegistrationRequestDto getRegistrationRequest() {
        return new UserRegistrationRequestDto("soniascott@gmail.com",
                "password03032004", "password03032004",
                "Sonia", "Scott", LocalDate.of(2004, 3, 3),
                "Lviv, Street Open", "+0994873436");
    }
}
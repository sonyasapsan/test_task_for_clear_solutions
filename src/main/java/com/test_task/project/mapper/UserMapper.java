package com.test_task.project.mapper;

import com.test_task.project.config.MapperConfig;
import com.test_task.project.dto.user.UpdateUserRequestDto;
import com.test_task.project.dto.user.UserRegistrationRequestDto;
import com.test_task.project.dto.user.UserRegistrationResponseDto;
import com.test_task.project.dto.user.UserResponseDto;
import com.test_task.project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toUserResponse(User user);

    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserResponseDto toUserResponseDto(User user);

    void updateUserFromDto(UpdateUserRequestDto dto, @MappingTarget User user);
}
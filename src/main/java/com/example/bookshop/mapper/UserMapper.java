package com.example.bookshop.mapper;

import com.example.bookshop.dto.UserDto;
import com.example.bookshop.dto.UserRegistrationRequestDto;
import com.example.bookshop.dto.UserResponseDto;
import com.example.bookshop.mapperconfig.MapperConfig;
import com.example.bookshop.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);

    UserResponseDto toUserResponceDto(User user);
}

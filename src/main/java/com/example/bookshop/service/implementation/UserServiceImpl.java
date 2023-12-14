package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.UserRegistrationRequestDto;
import com.example.bookshop.dto.UserResponseDto;
import com.example.bookshop.exceptions.RegistrationException;
import com.example.bookshop.mappers.UserMapper;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("This email is exist! Change your email!");
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        userRepository.save(user);
        return userMapper.toUserResponceDto(user);
    }
}

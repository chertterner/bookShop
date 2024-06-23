package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.UserRegistrationRequestDto;
import com.example.bookshop.dto.UserResponseDto;
import com.example.bookshop.exception.RegistrationException;
import com.example.bookshop.mapper.UserMapper;
import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.UserService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("This email is exist! Change your email!");
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(
                passwordEncoder.encode(userRegistrationRequestDto
                        .getPassword())
        );
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRole(Role.RoleName.USER);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponceDto(user);
    }
}

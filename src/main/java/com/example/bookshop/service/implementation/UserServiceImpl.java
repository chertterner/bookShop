package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import com.example.bookshop.dto.user.UserResponseDto;
import com.example.bookshop.exception.RegistrationException;
import com.example.bookshop.mapper.UserMapper;
import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.RoleRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.ShoppingCartService;
import com.example.bookshop.service.UserService;
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
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

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
        Role userRole = roleRepository.findByRole(Role.RoleName.USER);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        shoppingCartService.createShoppingCartWithUser(user);
        return userMapper.toUserResponceDto(user);
    }
}

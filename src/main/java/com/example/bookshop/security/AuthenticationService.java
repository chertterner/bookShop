package com.example.bookshop.security;

import com.example.bookshop.dto.UserLoginRequestDto;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    public boolean login(UserLoginRequestDto userLoginRequestDto) {
        Optional<User> user = userRepository.findByEmail(userLoginRequestDto.getEmail());
        return user.isPresent() && user.get()
                .getPassword()
                .equals(userLoginRequestDto.getPassword());
    }
}

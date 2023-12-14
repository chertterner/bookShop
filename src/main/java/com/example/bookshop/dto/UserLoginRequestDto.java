package com.example.bookshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @Email(message = "Provide a valid email")
    private String email;
    @NotBlank(message = "Type a password")
    @Size(min = 10, message = "Minimum length must be 10 symbols")
    private String password;
}

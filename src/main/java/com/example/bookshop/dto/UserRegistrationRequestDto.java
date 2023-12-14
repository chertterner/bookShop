package com.example.bookshop.dto;

import com.example.bookshop.annotation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "repeatPassword",
        message = "The password fields must match")
public class UserRegistrationRequestDto {
    @Email(message = "Provide a valid email")
    private String email;
    @NotBlank(message = "Type a password")
    @Size(min = 10, message = "Minimum length must be 10 symbols")
    private String password;
    @NotBlank
    @Size(min = 10, message = "Minimum length must be 10 symbols")
    private String repeatPassword;
    @NotBlank(message = "Type a first name")
    private String firstName;
    @NotBlank(message = "Type a last name")
    private String lastName;
    @NotBlank(message = "Type a shipping address")
    private String shippingAddress;
}

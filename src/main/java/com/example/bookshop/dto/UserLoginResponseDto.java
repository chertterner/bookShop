package com.example.bookshop.dto;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private String token;

    public UserLoginResponseDto(String userToken) {
        token = userToken;
    }
}

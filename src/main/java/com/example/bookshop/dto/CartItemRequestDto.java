package com.example.bookshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CartItemRequestDto {
    @NotNull
    private Long bookId;
    @Length(min = 1)
    private int quantity;
}

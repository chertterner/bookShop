package com.example.bookshop.dto;

import lombok.Data;

@Data
public class AddBookShoppingCartRequestDto {
    private long bookId;
    private int quantity;
}

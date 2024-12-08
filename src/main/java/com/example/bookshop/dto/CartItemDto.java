package com.example.bookshop.dto;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.ShoppingCart;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private ShoppingCart shoppingCart;
    private Book book;
    private int quantity;
}

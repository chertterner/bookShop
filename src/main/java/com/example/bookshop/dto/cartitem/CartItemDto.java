package com.example.bookshop.dto.cartitem;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.ShoppingCart;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class CartItemDto {
    @NonNull
    private Long id;
    @NonNull
    private ShoppingCart shoppingCart;
    @NonNull
    private Book book;
    @Positive
    private int quantity;
}

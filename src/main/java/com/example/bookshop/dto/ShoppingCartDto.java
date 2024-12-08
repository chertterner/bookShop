package com.example.bookshop.dto;

import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.User;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private User user;
    private Set<CartItem> cartItems;
}

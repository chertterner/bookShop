package com.example.bookshop.dto.cartitem;

import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.User;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    @NotNull
    private Long id;
    @NotNull
    private User user;
    @NotNull
    private Set<CartItem> cartItems;
}

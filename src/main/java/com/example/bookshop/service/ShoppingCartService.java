package com.example.bookshop.service;

import com.example.bookshop.dto.cartitem.CartItemRequestDto;
import com.example.bookshop.dto.cartitem.ShoppingCartDto;
import com.example.bookshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    Page<ShoppingCartDto> findAll(Pageable pageable);

    ShoppingCartDto save(CartItemRequestDto
                      cartItemRequestDto, User user);

    ShoppingCartDto updateBookQuantity(Long id, int quantity, User user);

    void deleteBook(Long id, User user);

    void createShoppingCartWithUser(User user);
}

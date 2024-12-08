package com.example.bookshop.service;

import com.example.bookshop.dto.AddBookShoppingCartRequestDto;
import com.example.bookshop.dto.ShoppingCartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface ShoppingCartService {
    Page<ShoppingCartDto> findAll(Pageable pageable);

    void save(AddBookShoppingCartRequestDto
                                 addBookShoppingCartRequestDto, UserDetails userDetails);

    void updateBookQuantity(Long id, int quantity, UserDetails userDetails);

    void deleteBook(Long id, UserDetails userDetails);
}

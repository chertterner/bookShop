package com.example.bookshop.repository;

import com.example.bookshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndShoppingCartId(Long id, Long shoppingCartId);

    CartItem findByBook_IdAndShoppingCartId(Long bookId, Long shoppingCartId);
}

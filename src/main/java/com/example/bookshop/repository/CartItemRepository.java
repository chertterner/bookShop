package com.example.bookshop.repository;

import com.example.bookshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndShoppingCart_Id(Long id, Long shoppingCartId);

    CartItem findByBook_IdAndShoppingCart_Id(Long bookId, Long shoppingCartId);
}

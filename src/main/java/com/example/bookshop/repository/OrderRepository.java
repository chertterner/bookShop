package com.example.bookshop.repository;

import com.example.bookshop.model.Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserId(Long userId);

    Order findByIdAndUserId(Long id, Long userId);

    Page<Order> findByIdAndUserId(Long id, Long userId, Pageable pageable);
}

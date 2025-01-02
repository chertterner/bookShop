package com.example.bookshop.repository;

import com.example.bookshop.model.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserId(Long userId);

    Optional<Order> findByIdAndUserId(Long id, Long userId);

}

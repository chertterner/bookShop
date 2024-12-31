package com.example.bookshop.repository;

import com.example.bookshop.model.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUser_Id(Long userId);

    Optional<Order> findByIdAndUser_Id(Long id, Long userId);
}

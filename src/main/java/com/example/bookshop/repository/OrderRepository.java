package com.example.bookshop.repository;

import com.example.bookshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUser_Id(Long userId);
}
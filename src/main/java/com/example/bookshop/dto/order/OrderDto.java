package com.example.bookshop.dto.order;

import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.Status;
import com.example.bookshop.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private User user;
    private Status status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItem> orderItems;
}

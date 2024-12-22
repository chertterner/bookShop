package com.example.bookshop.dto.order;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.Order;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Order order;
    private Book book;
    private int quantity;
    private BigDecimal price;
}

package com.example.bookshop.service;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.ShippingAddressRequestDto;
import com.example.bookshop.dto.order.UpdateStatusDto;
import com.example.bookshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> findAll(Pageable pageable);

    OrderDto save(ShippingAddressRequestDto shippingAddressRequestDto,
                         User user);

    OrderDto updateStatus(Long id, UpdateStatusDto updateStatusDto);

    Page<OrderItemDto> findAllOrderItems(Long id, Pageable pageable);

    Page<OrderItemDto> findSpecificOrderItem(Long id, Long itemId, Pageable pageable);
}

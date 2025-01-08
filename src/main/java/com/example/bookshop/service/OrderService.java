package com.example.bookshop.service;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.ShippingAddressDto;
import com.example.bookshop.dto.order.UpdateStatusDto;
import com.example.bookshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> findAll(Pageable pageable);

    OrderDto save(ShippingAddressDto shippingAddressDto,
                  User user);

    OrderDto updateStatus(Long id, UpdateStatusDto updateStatusDto);

    Page<OrderDto> findAllOrderItems(Long id, User user, Pageable pageable);

    OrderItemDto findSpecificOrderItem(Long orderId,
                                             Long itemId,
                                             User user,
                                             Pageable pageable);
}

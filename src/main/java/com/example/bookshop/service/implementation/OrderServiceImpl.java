package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.ShippingAddressRequestDto;
import com.example.bookshop.dto.order.UpdateStatusDto;
import com.example.bookshop.exception.OrderProcessingException;
import com.example.bookshop.mapper.OrderItemMapper;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.service.OrderService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderDto save(ShippingAddressRequestDto shippingAddressRequestDto,
                         User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(user.getId());
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("There is empty shopping cart!");
        }
        Optional<Order> order = Optional.ofNullable(
                orderRepository.findByUserId(
                                user.getId())
                        .orElseThrow(
                                () -> new OrderProcessingException("This order doesn't exist")
                        )
        );
        order.get()
                .setShippingAddress(shippingAddressRequestDto
                        .getShippingAddress());
        return orderMapper.toDto(orderRepository.save(order.get()));
    }

    @Override
    public OrderDto updateStatus(Long id, UpdateStatusDto updateStatusDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(
                        () -> new OrderProcessingException("This order doesn't exist")
                );
        order.setStatus(updateStatusDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Page<OrderItemDto> findAllOrderItems(Long id, User user, Pageable pageable) {
        Order order = orderRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new OrderProcessingException("This order doesn't exist"));
        return new PageImpl<>(order.getOrderItems()
                .stream()
                .map(orderItemMapper::toDto)
                .toList());
    }

    @Override
    public Page<OrderItemDto> findSpecificOrderItem(Long orderId,
                                                    Long itemId,
                                                    User user,
                                                    Pageable pageable) {
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new OrderProcessingException("This order doesn't exist"));
        return new PageImpl<>(order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getId().equals(itemId))
                .map(orderItemMapper::toDto)
                .toList()
        );
    }
}

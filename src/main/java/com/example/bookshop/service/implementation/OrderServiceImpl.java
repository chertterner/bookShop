package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.ShippingAddressDto;
import com.example.bookshop.dto.order.UpdateStatusDto;
import com.example.bookshop.exception.OrderProcessingException;
import com.example.bookshop.mapper.OrderItemMapper;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.Status;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public OrderDto save(
            ShippingAddressDto shippingAddressDto,
            User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(user.getId());
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderProcessingException(
                    "There is empty shopping cart#" + shoppingCart.getId()
            );
        }
        Order order = getFilledOrder(shippingAddressDto, user, shoppingCart);
        fillOrderOrderItems(shoppingCart, order);
        shoppingCartRepository.delete(shoppingCart);
        return orderMapper.toDto(orderRepository.save(order));
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
    public Page<OrderDto> findAllOrderItems(Long id, User user, Pageable pageable) {
        Page<Order> order = orderRepository.findByIdAndUserId(id, user.getId(), pageable);
        return order.map(orderMapper::toDto);
    }

    @Override
    public OrderItemDto findSpecificOrderItem(Long orderId,
                                              Long itemId,
                                              User user,
                                              Pageable pageable) {
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId());
        return order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getId().equals(itemId))
                .map(orderItemMapper::toDto).findFirst().get();
    }

    private Order getFilledOrder(ShippingAddressDto shippingAddressDto,
                                 User user, ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setShippingAddress(shippingAddressDto.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);
        order.setUser(user);
        order.setTotal(shoppingCart.getCartItems().stream().map(cartItem -> {
            BigDecimal price = cartItem.getBook().getPrice();
            long quantity = cartItem.getQuantity();
            return price.multiply(BigDecimal.valueOf(quantity));
        }).reduce(BigDecimal.ZERO, BigDecimal::add));
        return order;
    }

    private void fillOrderOrderItems(ShoppingCart shoppingCart, Order order) {
        Set<CartItem> cartItemSet = shoppingCart.getCartItems();
        cartItemSet.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            order.getOrderItems().add(orderItem);
            return order;
        });
    }
}

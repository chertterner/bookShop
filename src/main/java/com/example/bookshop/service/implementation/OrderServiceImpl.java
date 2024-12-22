package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.ShippingAddressRequestDto;
import com.example.bookshop.dto.order.UpdateStatusDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.OrderItemMapper;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.OrderRepository;
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

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        return new PageImpl<>(orderRepository.findAll(pageable)
                .stream()
                .map(orderMapper::toDto)
                .toList());
    }

    @Override
    public OrderDto save(ShippingAddressRequestDto shippingAddressRequestDto,
                                User user) {
        Order order = orderRepository.findByUser_Id(user.getId());

        if (order == null) {
            throw new EntityNotFoundException("This order doesn't exist");
        } else {
            order.setShippingAddress(shippingAddressRequestDto.getShippingAddress());
        }
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto updateStatus(Long id, UpdateStatusDto updateStatusDto) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            throw new EntityNotFoundException("This order doesn't exist");
        } else {
            order.get().setStatus(updateStatusDto.getStatus());
        }
        return orderMapper.toDto(orderRepository.save(order.get()));
    }

    @Override
    public Page<OrderItemDto> findAllOrderItems(Long id, Pageable pageable) {
        return new PageImpl<>(orderRepository.findById(id)
                .get()
                .getOrderItems()
                .stream()
                .map(orderItemMapper::toDto)
                .toList());
    }

    @Override
    public Page<OrderItemDto> findSpecificOrderItem(Long id, Long itemId, Pageable pageable) {
        return new PageImpl<>(orderRepository.findById(id)
                .get()
                .getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getId()
                        .equals(itemId))
                .map(orderItemMapper::toDto)
                .toList());
    }
}

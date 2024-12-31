package com.example.bookshop.controller;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.ShippingAddressRequestDto;
import com.example.bookshop.dto.order.UpdateStatusDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders management",
        description = "Endpoints for orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    @Operation(summary = "Get all user's orders",
            description = "Find all user's orders")
    @GetMapping
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @Operation(summary = "Add shipping address",
            description = "Add shipping address")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto addShippingAddress(
            @RequestBody @Valid ShippingAddressRequestDto
                    shippingAddressRequestDto,
            @AuthenticationPrincipal User user) {
        return orderService.save(shippingAddressRequestDto, user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update order status", description = "Update status")
    @PatchMapping("/{id}")
    public OrderDto updateStatus(@PathVariable Long id,
                                              @RequestBody UpdateStatusDto updateStatusDto) {
        return orderService.updateStatus(id, updateStatusDto);
    }

    @Operation(summary = "Get all order items for a specific order",
            description = "Retrieve all order items for a specific order")
    @GetMapping("/{id}/items")
    public Page<OrderItemDto> getAllOrderItems(@PathVariable Long id,
                                               @AuthenticationPrincipal User user,
                                               Pageable pageable) {
        return orderService.findAllOrderItems(id, user, pageable);
    }

    @Operation(summary = "Get a specific order item within an order",
            description = "Retrieve a specific order item within an order")
    @GetMapping("/{id}/items/{itemId}")
    public Page<OrderItemDto> getSpecificOrder(@PathVariable Long orderId,
                                               @PathVariable Long itemId,
                                               @AuthenticationPrincipal User user,
                                               Pageable pageable) {
        return orderService.findSpecificOrderItem(orderId, itemId, user, pageable);
    }
}

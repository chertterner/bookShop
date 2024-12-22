package com.example.bookshop.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShippingAddressRequestDto {
    @NotNull
    private String shippingAddress;
}

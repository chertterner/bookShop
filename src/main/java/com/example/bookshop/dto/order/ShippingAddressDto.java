package com.example.bookshop.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShippingAddressDto {
    @NotBlank
    private String shippingAddress;
}

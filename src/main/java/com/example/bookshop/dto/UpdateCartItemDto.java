package com.example.bookshop.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateCartItemDto {
    @Length(min = 1)
    private int quantity;
}

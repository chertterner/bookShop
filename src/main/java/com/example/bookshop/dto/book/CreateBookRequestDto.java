package com.example.bookshop.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}

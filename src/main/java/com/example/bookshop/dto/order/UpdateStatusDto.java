package com.example.bookshop.dto.order;

import com.example.bookshop.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStatusDto {
    @NotBlank
    private Status status;
}

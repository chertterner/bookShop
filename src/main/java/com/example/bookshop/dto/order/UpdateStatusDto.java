package com.example.bookshop.dto.order;

import com.example.bookshop.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusDto {
    @NotNull
    private Status status;
}
